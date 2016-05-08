package ch.swingfx.awt;


import ch.swingfx.twinkle.NotificationBuilder;
import ch.swingfx.twinkle.event.NotificationEvent;
import ch.swingfx.twinkle.event.NotificationEventAdapter;
import ch.swingfx.twinkle.style.INotificationStyle;
import ch.swingfx.twinkle.style.closebutton.RoundCloseButton;
import ch.swingfx.twinkle.style.theme.DarkDefaultNotification;
import ch.swingfx.twinkle.window.Positions;

import javax.swing.*;

public class QuickStart {

	public static void main(String[] args) {
		// AA the text
		System.setProperty("swing.aatext", "true");

		// First we define the style/theme of the window.
		// Note how we override the default values
		INotificationStyle style = new DarkDefaultNotification()
				.withWidth(400) // Optional
				.withAlpha(0.9f) // Optional
		;

		// Now lets build the notification
		new NotificationBuilder()
				.withStyle(style) // Required. here we set the previously set style
				.withTitle("Set a title") // Required.
				.withMessage("Set a message") // Optional
				.withIcon(new ImageIcon(RoundCloseButton.class.getClassLoader().getResource("com/demo/head_0.png"))) // Optional. You could also use a String path
				.withDisplayTime(2000) // Optional
				.withPosition(Positions.CENTER) // Optional. Show it at the center of the screen
				.withListener(new NotificationEventAdapter() { // Optional
					public void closed(NotificationEvent event) {
						System.out.println("closed notification with UUID " + event.getId());
					}

					public void clicked(NotificationEvent event) {
						System.out.println("clicked notification with UUID " + event.getId());
					}
				})
				.showNotification(); // this returns a UUID that you can use to identify events on the listener

	}

}
