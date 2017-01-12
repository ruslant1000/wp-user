package kz.tem.portal.user.messages;

import kz.tem.portal.explorer.panel.common.component.message.AbstractMessage;
import kz.tem.portal.server.plugin.Module;
import kz.tem.portal.server.plugin.ModuleConfig;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

@SuppressWarnings("serial")
public class Message extends Module{

	private static String MESSAGE_TYPE="Message type (info or error)";
	
	private static String MESSAGE_TEXT="Message text";
	
	private String messageType;
	private String messageText;
	
	
	public Message(String id, ModuleConfig config) {
		super(id,config);
		
		messageType = getModuleConfig().getValues().get(MESSAGE_TYPE);
		messageText = getModuleConfig().getValues().get(MESSAGE_TEXT);
		
		
	}
	

	@Override
	public void create() throws Exception {
		Message.this.removeAll();
		
		if(messageType!=null){
			if(messageType.toLowerCase().equals("info")){
				add(AbstractMessage.info("message", messageText));
			}else if(messageType.toLowerCase().equals("error")){
				add(AbstractMessage.error("message", messageText));
			}
		}
	}


	@Override
	public void initDefaultConfigs(ModuleConfig config) throws Exception {
		super.initDefaultConfigs(config);
		config.addDefaultConfig(MESSAGE_TYPE, "info");
		config.addDefaultConfig(MESSAGE_TEXT, "write your text here");
		
	}
	
}
