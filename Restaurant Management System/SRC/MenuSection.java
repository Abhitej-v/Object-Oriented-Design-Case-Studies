package OODPracticeExample.ResturantManagementSystem;

import java.util.ArrayList;

public class MenuSection extends MenuComponent {
    ArrayList<MenuComponent> subSections = new ArrayList<MenuComponent>();

    @Override
    public void addMenuComponent(MenuComponent menuComponent) {
        subSections.add(menuComponent);
    }

    @Override
    public void removeMenuComponent(MenuComponent menuComponent) {
        subSections.remove(menuComponent);
    }

    public MenuComponent getChild(int i) {
        return subSections.get(i);
    }

    public void print() {
        System.out.println("Section Name " + name + " Section description " + description);
        for (MenuComponent menuComponent : subSections) {
            menuComponent.print();
        }
    }
}
