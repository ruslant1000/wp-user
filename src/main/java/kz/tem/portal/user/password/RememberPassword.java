package kz.tem.portal.user.password;

import org.apache.wicket.model.PropertyModel;

import kz.tem.portal.api.NotificationsEngine;
import kz.tem.portal.api.RegisterEngine;
import kz.tem.portal.explorer.panel.common.component.message.AbstractMessage;
import kz.tem.portal.explorer.panel.common.form.DefaultInputStatelesForm;
import kz.tem.portal.server.model.User;
import kz.tem.portal.server.plugin.Module;
import kz.tem.portal.server.plugin.ModuleConfig;

@SuppressWarnings("serial")
public class RememberPassword extends Module{

	
	private String email;
	
	public RememberPassword(String id, ModuleConfig config) {
		super(id, config);
		
	}

	@Override
	public void create() throws Exception {
		RememberPassword.this.removeAll();
		DefaultInputStatelesForm form = new DefaultInputStatelesForm("form"){

			@Override
			public String submitButtonName() {
				return "Восстановить";
			}

			@Override
			public void onSubmit() throws Exception {
				super.onSubmit();
				Thread.sleep(3000);
				User user =  RegisterEngine.getInstance().getUserRegister().getUserByEmail(email);
				if(user==null)
					throw new Exception("Указанный e-mail не зарегистрирован");
				
				NotificationsEngine.get().emailUserRememberPassword(user);
				AbstractMessage mess = AbstractMessage.info("form", "На указанный Вами e-mail отправлено сообщение с инструкциями по восстановлению пароля.");
				mess.setOutputMarkupId(true);
				RememberPassword.this.get("form").replaceWith(mess);
			}
			
		};
		add(form);
		form.addFieldString("E-mail", new PropertyModel<String>(RememberPassword.this, "email"), true);
		
	}

	@Override
	public ModuleConfig getModuleConfig() {
		return super.getModuleConfig();
	}
	
	

}
