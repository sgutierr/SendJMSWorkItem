package org.jbpm.workitem.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.naming.NamingException;

import org.apache.camel.CamelExecutionException;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


public class SendMessageHandler extends AbstractLogOrThrowWorkItemHandler {

	private KieSession ksession;
	private SendMessage sendMessage;
	public static final String THIS_MESSAGE = "messageIn";
	public static final String THIS_INCIDENCE = "incidence";
	public static final String THIS_RESUME = "resumeTask";
	
	
	
	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager workItemmanager) {
		workItemmanager.abortWorkItem(workItem.getId());

	}

	public SendMessageHandler(KieSession ksession) throws NamingException {
		
		super();
		this.ksession = ksession;
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext(); 
		this.sendMessage = (SendMessage)context.getBean("sendMessage");
		
	}

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager workItemManager) {		
		
		Map <String,Object> output = new HashMap<String, Object>();	
		String message = (String) workItem.getParameter(THIS_MESSAGE);
		String incidence = (String) workItem.getParameter(THIS_INCIDENCE);
		String resumeTask = (String) workItem.getParameter(THIS_RESUME);
		Integer id = (int) workItem.getId();
		
		try {
			sendMessage.sendMessage(message);
 			output.put("status", "Ok");
			ksession.getWorkItemManager().completeWorkItem(id, output);
			
		} catch (ExecutionException e) {
			handleException(e);
	
		} catch (InterruptedException e) {
			handleException(e);
		}
		  catch (CamelExecutionException e) {
			if ("resume".equals(resumeTask)){
				output.put("status", "Fail");  
				ksession.getWorkItemManager().completeWorkItem(id, output);
				if (incidence == null) {
					incidence=THIS_INCIDENCE;
				}
				ksession.signalEvent(incidence, new String(e.getMessage()));			
			}
			else {
				handleException(e);
			}
			
		}
		
	}

	public KieSession getKsession() {
		return ksession;
	}

	public void setKsession(KieSession ksession) {
		this.ksession = ksession;
	}

	public SendMessage getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(SendMessage sendMessage) {
		this.sendMessage = sendMessage;
	}
	

}
