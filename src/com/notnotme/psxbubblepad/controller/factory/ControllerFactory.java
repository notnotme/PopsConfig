package com.notnotme.psxbubblepad.controller.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javafx.application.HostServices;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author romain
 *
 * Intended to create custom FXML Controller (see FXMLController class)
 * The controllers usually need a reference to their Stage and other things
 * so this factory is used to give those ojects to the controller constructors
 */
public class ControllerFactory implements Callback<Class<?>,Object> {

	private final Stage mStage;
	private final HostServices mHostServices;

	public ControllerFactory(Stage stage) {
		mStage = stage;
		mHostServices = null;
	}

	public ControllerFactory(HostServices hostServices) {
		mStage = null;
		mHostServices = hostServices;
	}

	public ControllerFactory(HostServices hostServices, Stage stage) {
		mStage = stage;
		mHostServices = hostServices;
	}

	@Override
	public Object call(Class<?> type) {
		try {
			for (Constructor<?> c : type.getConstructors()) {
				Class<?>[] params = c.getParameterTypes();
				int paramCount = c.getParameterCount();

				if (paramCount == 1) {
					if(params[0] == HostServices.class) {
						return c.newInstance(mHostServices) ;
					} else if (params[0] == Stage.class) {
						return c.newInstance(mStage) ;
					}
				}
				if (paramCount == 2) {
					if(params[0] == HostServices.class && params[1] == Stage.class) {
						return c.newInstance(mHostServices, mStage);
					}
					if(params[1] == HostServices.class && params[2] == Stage.class) {
						return c.newInstance(mStage, mHostServices);
					}
				}
			}
		} catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}

		throw new RuntimeException("No constructor found");
	}

}