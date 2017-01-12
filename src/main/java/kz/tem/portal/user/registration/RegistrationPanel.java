package kz.tem.portal.user.registration;

import kz.tem.portal.explorer.panel.common.component.message.AbstractMessage;
import kz.tem.portal.explorer.panel.common.form.DefaultInputStatelesForm;
import kz.tem.portal.server.model.User;
import kz.tem.portal.server.plugin.Module;
import kz.tem.portal.server.plugin.ModuleConfig;

import org.apache.wicket.model.PropertyModel;

@SuppressWarnings("serial")
public class RegistrationPanel extends Module{

	private User user;
	private String password2;
	
	public RegistrationPanel(String id, ModuleConfig config) {
		super(id,config);
		setOutputMarkupId(true);
		
		
	}

	@Override
	public void create() throws Exception {
		RegistrationPanel.this.removeAll();
		user = new User();
		
		
		
		DefaultInputStatelesForm form = new DefaultInputStatelesForm("form"){

			@Override
			public void onSubmit() throws Exception {
				if(password2!=null && user.getPassword()!=null && !password2.equals(user.getPassword()))
					throw new Exception("Пароли не совпадают");
				
				
				AbstractMessage mess = AbstractMessage.info("form", "Вы успешно зарегистрировались на портале. На указанный e-mail отправлено сообщение с инструкциями для подтверждения регистрации.");
				mess.setOutputMarkupId(true);
				RegistrationPanel.this.get("form").replaceWith(mess);
				
//				RegisterEngine.getInstance().getUserRegister().addNewUser(user);
				
			}

			@Override
			public String submitButtonName() {
				return "Зарегистрировать";
			}


		};
		form.addFieldString("E-Mail", new PropertyModel<String>(user, "email"), true);
		form.addFieldString("login", new PropertyModel<String>(user, "login"), true);
		form.addFieldPassword("Пароль", new PropertyModel<String>(user, "password"), true);
		form.addFieldPassword("Еще раз пароль", new PropertyModel<String>(RegistrationPanel.this, "password2"), true);
		add(form);
		form.setOutputMarkupId(true);
	}
	
	

}
