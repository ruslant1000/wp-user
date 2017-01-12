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
public class NewPassword extends Module{

	
	private String pass1;
	private String pass2;
	
	public NewPassword(String id, ModuleConfig config) {
		super(id, config);
		
	}

	@Override
	public void create() throws Exception {
		NewPassword.this.removeAll();
		DefaultInputStatelesForm form = new DefaultInputStatelesForm("form"){

			@Override
			public String submitButtonName() {
				return "Восстановить";
			}

			@Override
			public void onSubmit() throws Exception {
				super.onSubmit();
				
				
				if(!pass1.equals(pass2))
					throw new Exception("Введенные пароли не совпадают");
				
				
				AbstractMessage mess = AbstractMessage.info("form", "Пароль успешно восстановлен");
				mess.setOutputMarkupId(true);
				NewPassword.this.get("form").replaceWith(mess);
			}
			
		};
		add(form);
		form.addFieldString("Новый пароль", new PropertyModel<String>(NewPassword.this, "pass1"), true);
		form.addFieldString("Еще раз новый пароль", new PropertyModel<String>(NewPassword.this, "pass2"), true);
		
	}

	@Override
	public ModuleConfig getModuleConfig() {
		return super.getModuleConfig();
	}
	
	

}
