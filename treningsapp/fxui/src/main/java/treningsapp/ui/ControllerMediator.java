package treningsapp.ui;

import treningsapp.core.UserHandler;

public class ControllerMediator implements InterfaceMediateControllers {
  private RegisterController registerController;
  private AddSessionController addSessionController;

  @Override
  public void registerController(RegisterController registerController) {
    this.registerController = registerController;
  }

  @Override
  public void registerController(AddSessionController addSessionController) {
    this.addSessionController = addSessionController;
  }

  private ControllerMediator() {
  }

  public static ControllerMediator getInstance() {
    return ControllerMediatorHolder.INSTANCE;
  }

  private static class ControllerMediatorHolder {
    private static final ControllerMediator INSTANCE = new ControllerMediator();
  }

  public UserHandler getUserHandler() {
    return registerController.getTreningsAppAccess().getUserHandler();
  }

  public AddSessionController getAddSessionController() {
    return this.addSessionController;
  }

  public RegisterController getRegisterController() {
    return this.registerController;
  }


}
