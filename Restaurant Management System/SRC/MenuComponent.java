package OODPracticeExample.ResturantManagementSystem;

public abstract class MenuComponent {
    String name;
    String description;

    public void addMenuComponent(MenuComponent menuComponent) {
        throw new UnsupportedOperationException();
    }

    public void removeMenuComponent(MenuComponent menuComponent) {
        throw new UnsupportedOperationException();
    }

    public MenuComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    public String getDescription() {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public double getPrice() {
        throw new UnsupportedOperationException();
    }

    public boolean isVegetarian() {
        throw new UnsupportedOperationException();
    }

    public void print() {
        throw new UnsupportedOperationException();
    }
}
