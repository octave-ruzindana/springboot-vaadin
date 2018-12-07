package be.octave.springbootvaadin.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MainLayout extends AppLayout {

    public MainLayout() {
        this.setBranding(new H3("ULTRA TODO"));
        //this.setMenu(new HorizontalLayout(VaadinIcon.SIGN_OUT.create()));

        AppLayoutMenu appLayoutMenu = new AppLayoutMenu();
//        appLayoutMenu.addMenuItem(VaadinIcon.SIGN_OUT.create(),"Sign Out","login");
        this.setMenu(appLayoutMenu);

        // AppLayoutMenu menu = this.createMenu();

        //menu.addMenuItem("Some thing");
/*
        menu.addMenuItems(
//                new AppLayoutMenuItem(VaadinIcon.USER.create(), "My Profile", "profile"),
//                new AppLayoutMenuItem(VaadinIcon.TRENDING_UP.create(), "Trending Topics", "trends"),
                new AppLayoutMenuItem(VaadinIcon.SIGN_OUT.create(), "Sign Out", e -> logout()));
*/

    }


}
