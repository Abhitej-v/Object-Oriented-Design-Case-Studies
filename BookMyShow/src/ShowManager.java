package OODPracticeExample.MovieTicketBooking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowManager {
    private HashMap<Show, HashMap<String,ShowSeat>> showToShowSeatsMap= new HashMap<Show, HashMap<String, ShowSeat>>();
    private HashMap<String, Show> showIdShowMap=new HashMap<>();
    private HashMap<Show, ArrayList<Ticket>> showTicketsMap=new HashMap<>();
    private volatile static ShowManager showMgrUnqInst;
    private ShowManager() {
    }

    public static ShowManager getShowMgrInstance() {
        if (showMgrUnqInst == null) {
            synchronized (ShowManager.class) {
                if (showMgrUnqInst == null) {
                    showMgrUnqInst = new ShowManager();
                }
            }
        }
        return showMgrUnqInst;
    }

    private boolean checkReqSeatAvailable(ArrayList<ShowSeat> showSeats) {
        boolean flag = true;
        for (ShowSeat showSeat : showSeats) {
            if (showSeat.getShowSeatStatus() == ShowSeatStatus.Reserved ||
                    showSeat.getShowSeatStatus() == ShowSeatStatus.PendingPayment) {
                flag = false;
            }
        }
        return flag;
    }
    public static void changeSeatStatus(ShowSeatStatus showSeatStatus, List<ShowSeat> showSeats) {
        for (ShowSeat showSeat : showSeats) {
            showSeat.setShowSeatStatus(showSeatStatus);
        }
    }


    public boolean validateToBlockSeats(ArrayList<ShowSeat> showSeats) {
        if (checkReqSeatAvailable(showSeats)){
            changeSeatStatus(ShowSeatStatus.PendingPayment,showSeats);
            return true;
        }
        else {
            return false;
        }
    }

    public ArrayList<ShowSeat> getShowSeatsFromSeatIdList(String showId,ArrayList<String> seatNumbers){
        Show show=showIdShowMap.get(showId);
        ArrayList<ShowSeat> reqShowSeats=new ArrayList<>();
        for (String seatNum:
                seatNumbers) {
            ShowSeat showSeat=showToShowSeatsMap.get(show).getOrDefault(seatNum,null);
            if (showSeat!=null){
                reqShowSeats.add(showSeat);
            }
            else {
                throw new IllegalArgumentException("Invalid seat number"+ seatNum);
            }
        }
        return reqShowSeats;
    }

    public Show getShow(String showId) {
        return showIdShowMap.get(showId);
    }

    public void addTicketForShow(Show show, Ticket ticket) {
        showTicketsMap.getOrDefault(show,new ArrayList<Ticket>()).add(ticket);
    }

    public boolean cancelShow(Show show) {
        // check if show hasn't yet started and iterate through all the tickets in ticketsOfShow and trigger
        // cancelTicket() on them.
        // cancel method notifies system, system notifies customer
        ArrayList<Ticket> ticketsOfShow=showTicketsMap.get(show);
        for (Ticket ticket : ticketsOfShow) {
            ticket.cancel();
        }
        show.setShowStatus(ShowStatus.Cancelled);
        Theatre theatre=show.getTheatre();
        theatre.removeShow(show);
        return true;
    }

    public void removeTicketForShow(Show show, Ticket ticket) {
        showTicketsMap.get(show).remove(ticket);
    }
}
