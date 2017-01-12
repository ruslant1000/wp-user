package kz.tem.portal.user.login;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.http.handler.RedirectRequestHandler;

import kz.tem.portal.explorer.panel.common.form.DefaultInputForm;
import kz.tem.portal.explorer.panel.common.form.DefaultInputStatelesForm;
import kz.tem.portal.explorer.panel.common.form.IFormSubmitButtonListener;
import kz.tem.portal.server.plugin.Module;
import kz.tem.portal.server.plugin.ModuleConfig;

@SuppressWarnings("serial")
public class LoginPanel extends Module{

	
	private static String REGISTRATION_PAGE_URL="Registration page URL";
	private static String KABINET_PAGE_URL="Kabinet page URL";
	private static String MAIN_PAGE_URL="Main page URL";
	private static String REMEMBER_PASS_PAGE_URL="Remember password page URL";
	
	private String registrationPageUrl;
	private String kabinetPageUrl;
	private String mainPageUrl;
	private String rememberPassPageUrl;
	
	private String login;
	private String password;
	
	public LoginPanel(String id, ModuleConfig config) {
		super(id, config);
		setOutputMarkupId(true);
		registrationPageUrl = getModuleConfig().getValues().get(REGISTRATION_PAGE_URL);
		kabinetPageUrl = getModuleConfig().getValues().get(KABINET_PAGE_URL);
		mainPageUrl = getModuleConfig().getValues().get(MAIN_PAGE_URL);
		rememberPassPageUrl = getModuleConfig().getValues().get(REMEMBER_PASS_PAGE_URL);
	}

	@Override
	public void create() throws Exception {
		LoginPanel.this.removeAll();
		DefaultInputStatelesForm form = new DefaultInputStatelesForm("form"){

			
			@Override
			public String submitButtonName() {
				return "Войти";
			}

			@Override
			public void onSubmit() throws Exception {
				boolean authResult = AuthenticatedWebSession.get().signIn(
						login, password);
				if(authResult){
					System.out.println("ORIGINAL URL: "+RestartResponseAtInterceptPageException.getOriginalUrl());
					if(RestartResponseAtInterceptPageException.getOriginalUrl()!=null)
						continueToOriginalDestination();
					else
						throw new RedirectToUrlException(kabinetPageUrl);
				}else
					error("Неверный логин или пароль");
			}

			
		};
		form.addFieldString("Login", new PropertyModel<String>(LoginPanel.this, "login"), true);
		form.addFieldString("Password", new PropertyModel<String>(LoginPanel.this, "password"), true);
		add(form);
		add(new WebMarkupContainer("reg").add(new AttributeModifier("href", registrationPageUrl)));
		add(new WebMarkupContainer("remember").add(new AttributeModifier("href", rememberPassPageUrl)));
		add(new WebMarkupContainer("main").add(new AttributeModifier("href", mainPageUrl)));
	}
	
	@Override
	public void initDefaultConfigs(ModuleConfig config) throws Exception {
		super.initDefaultConfigs(config);
		config.addDefaultConfig(REGISTRATION_PAGE_URL, "/registration");
		config.addDefaultConfig(KABINET_PAGE_URL, "/kabinet");
		config.addDefaultConfig(MAIN_PAGE_URL, "/");
		config.addDefaultConfig(REMEMBER_PASS_PAGE_URL, "/rememberpassword");
		
	}

}
