package be.octave.springbootvaadin.components;

import be.octave.springbootvaadin.views.LoginView;
import be.octave.springbootvaadin.views.MainView;
import com.github.appreciated.app.layout.behaviour.AppLayout;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.appmenu.left.LeftNavigationComponent;
import com.github.appreciated.app.layout.component.appmenu.left.LeftSubmenuComponent;
import com.github.appreciated.app.layout.component.appmenu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.appmenu.left.builder.LeftSubMenuBuilder;
import com.github.appreciated.app.layout.design.AppLayoutDesign;
import com.github.appreciated.app.layout.notification.DefaultNotificationHolder;
import com.github.appreciated.app.layout.notification.component.AppBarNotificationButton;
import com.github.appreciated.app.layout.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;


@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainLayout extends AppLayoutRouterLayout implements LocaleChangeObserver {

    private Logger LOG = LoggerFactory.getLogger(MainLayout.class);

    private LeftNavigationComponent homeMenuItem;
    private LeftNavigationComponent signOutMenuItem;
    private LeftNavigationComponent completedTasksSubMenuItem;
    private LeftNavigationComponent ongoingTasksSubMenuItem;

    @Override
    public void localeChange(LocaleChangeEvent localeChangeEvent) {
        LOG.info("Locale changed to  {}", localeChangeEvent.getLocale());

        homeMenuItem.setText(getTranslation("menu.home"));
        signOutMenuItem.setText(getTranslation("menu.signout"));
        completedTasksSubMenuItem.setText(getTranslation("menu.tasks.completed"));
        ongoingTasksSubMenuItem.setText(getTranslation("menu.tasks.ongoing"));
    }

    @Override
    public AppLayout getAppLayout() {
        DefaultNotificationHolder notifications = new DefaultNotificationHolder(newStatus -> {
        });

        homeMenuItem = new LeftNavigationComponent(getTranslation("menu.home"), VaadinIcon.HOME.create(), MainView.class);
        signOutMenuItem = new LeftNavigationComponent(getTranslation("menu.signout"), VaadinIcon.SIGN_OUT.create(), LoginView.class);

        completedTasksSubMenuItem = new LeftNavigationComponent(getTranslation("menu.tasks.completed"), VaadinIcon.CHECK.create(), MainView.class);
        ongoingTasksSubMenuItem = new LeftNavigationComponent(getTranslation("menu.tasks.ongoing"), VaadinIcon.SPINNER.create(), MainView.class);

        return AppLayoutBuilder
                .get(Behaviour.LEFT_HYBRID)
                .withTitle("Ultra Todo")
                .withAppBar(AppBarBuilder
                        .get()
                        .add(new LanguageChooser())
                        .add(new AppBarNotificationButton(VaadinIcon.BELL, notifications))
                        .build())
                .withDesign(AppLayoutDesign.MATERIAL)
                .withAppMenu(LeftAppMenuBuilder
                        .get()
                        .add(homeMenuItem)
                        .add(signOutMenuItem)
                        .add(LeftSubMenuBuilder.get(getTranslation("menu.tasks"), VaadinIcon.TASKS.create())
                                .add(completedTasksSubMenuItem)
                                .add(ongoingTasksSubMenuItem)
                                .build()
                        )
                        .build())
                .withIconComponent(VaadinIcon.NOTEBOOK.create())
                .build();

    }
}
