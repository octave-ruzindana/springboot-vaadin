package be.octave.springbootvaadin.components;

;
import be.octave.springbootvaadin.LoginView;
import be.octave.springbootvaadin.MainView;
import com.github.appreciated.app.layout.behaviour.AppLayout;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.appmenu.MenuBadgeComponent;
import com.github.appreciated.app.layout.component.appmenu.MenuHeaderComponent;
import com.github.appreciated.app.layout.component.appmenu.left.LeftClickableComponent;
import com.github.appreciated.app.layout.component.appmenu.top.TopClickableComponent;
import com.github.appreciated.app.layout.component.appmenu.top.TopNavigationComponent;
import com.github.appreciated.app.layout.component.appmenu.top.builder.TopAppMenuBuilder;
import com.github.appreciated.app.layout.design.AppLayoutDesign;
import com.github.appreciated.app.layout.entity.DefaultBadgeHolder;
import com.github.appreciated.app.layout.notification.DefaultNotificationHolder;
import com.github.appreciated.app.layout.notification.component.AppBarNotificationButton;
import com.github.appreciated.app.layout.router.AppLayoutRouterLayout;
import com.github.appreciated.app.layout.webcomponents.appmenu.AppSubmenu;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;


import static com.github.appreciated.app.layout.entity.Section.FOOTER;
import static com.github.appreciated.app.layout.entity.Section.HEADER;

@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainLayout extends AppLayoutRouterLayout {

    @Override
    public AppLayout getAppLayout() {
        DefaultNotificationHolder notifications = new DefaultNotificationHolder(newStatus -> {
        });

        return AppLayoutBuilder
                .get(Behaviour.TOP)
                .withTitle("Ultra Todo")
                .withAppBar(AppBarBuilder
                        .get()
                        .add(new AppBarNotificationButton(VaadinIcon.BELL, notifications))
                        .build())
                .withDesign(AppLayoutDesign.MATERIAL)
                .withAppMenu(TopAppMenuBuilder
                        .get()
                        .add(new TopNavigationComponent("Home", VaadinIcon.HOME.create(), MainView.class))
                        .add(new TopNavigationComponent("Sign In", VaadinIcon.SIGN_IN.create(), LoginView.class))
                        /*
                        .addToSection(new MenuHeaderComponent("Menu-Header",
                                "Version 2.0.1",
                                "/logo.png"
                        ), HEADER)

                        .addToSection(new LeftClickableComponent("Footer Clickable!",
                                VaadinIcon.COG.create(),
                                clickEvent -> Notification.show("Clicked!")
                        ), FOOTER)
                        */
                        .build())
                .withIconComponent(VaadinIcon.NOTEBOOK.create())
                .build();
    }

}
