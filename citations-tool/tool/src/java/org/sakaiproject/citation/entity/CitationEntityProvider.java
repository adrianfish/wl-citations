package org.sakaiproject.citation.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.sakaiproject.citation.api.Citation;
import org.sakaiproject.citation.api.CitationCollection;
import org.sakaiproject.citation.api.CitationService;
import org.sakaiproject.content.api.ContentHostingService;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.entity.api.EntityPermissionException;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.entitybroker.EntityView;
import org.sakaiproject.entitybroker.entityprovider.annotations.EntityCustomAction;
import org.sakaiproject.entitybroker.entityprovider.capabilities.ActionsExecutable;
import org.sakaiproject.entitybroker.entityprovider.capabilities.AutoRegisterEntityProvider;
import org.sakaiproject.entitybroker.entityprovider.capabilities.Outputable;
import org.sakaiproject.entitybroker.exception.EntityException;
import org.sakaiproject.entitybroker.exception.EntityNotFoundException;
import org.sakaiproject.entitybroker.util.AbstractEntityProvider;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.exception.PermissionException;
import org.sakaiproject.exception.ServerOverloadException;
import org.sakaiproject.exception.TypeException;


/**
 * Citations service is built on top of resources. All permissions checks are handled
 * by resources (ContentHostingService). Nothing that accepts the Citations List ID
 * should be exposed as it would allow security checks to be bypassed.
 *
 */
public class CitationEntityProvider extends AbstractEntityProvider implements AutoRegisterEntityProvider, ActionsExecutable, Outputable {

	private CitationService citationService;
	private ContentHostingService contentHostingService;
	
	public void setCitationService(CitationService citationService) {
		this.citationService = citationService;
	}

	
	public void setContentHostingService(ContentHostingService contentHostingService) {
		this.contentHostingService = contentHostingService;
	}


	public String getEntityPrefix() {
		return "citation";
	}
	
	
	
	@EntityCustomAction(action="list", viewKey=EntityView.VIEW_LIST)
	public DecoratedCitationCollection getCitationList(EntityView view, Map<String, Object> params) {
		StringBuilder resourceId = new StringBuilder();
		String[] segments = view.getPathSegments();
		for(int i = 2; i < segments.length; i++) {
			resourceId.append("/");
			resourceId.append(segments[i]);
		}
		if (resourceId.length() == 0) {
			throw new EntityNotFoundException("You must supply a path to the citation list.", null);
		}
		try {
			ContentResource resource = contentHostingService.getResource(resourceId.toString());
			
			if (!CitationService.CITATION_LIST_ID.equals(resource.getResourceType())) {
				throw new EntityException("Not a citation list",resourceId.toString(), 400);
			}
			if(resource.getContentLength() > 1024) {
				throw new EntityException("Bad citation list", resourceId.toString(), 400);
			}
     		String citationCollectionId = new String( resource.getContent() );
    		CitationCollection collection = citationService.getCollection(citationCollectionId);

			ResourceProperties properties = resource.getProperties();

    		String title = properties.getProperty(ResourceProperties.PROP_DISPLAY_NAME);
    		String description = properties.getProperty( ResourceProperties.PROP_DESCRIPTION );
    		
    		DecoratedCitationCollection dCollection = new DecoratedCitationCollection(title, description);
    		
    		for(Citation citation: (List<Citation>)collection.getCitations()) {
    			citation.getPrimaryUrl()
    			dCollection.addCitation(new DecoratedCitation(citation.getSchema().getIdentifier(), citation.getCitationProperties()));
    		}
    		return dCollection;
    	} catch (PermissionException e) {
			// TODO User logged in?
			throw new EntityException("Permission denied", resourceId.toString(), 401);
		} catch (IdUnusedException e) {
			throw new EntityException("Not found", resourceId.toString(), 404);
		} catch (TypeException e) {
			throw new EntityException("Wrong type", resourceId.toString(), 400);
		} catch (ServerOverloadException e) {
			throw new EntityException("Server Overloaded", resourceId.toString(), 500);
		}
		
	}
	//
	/**
	 * This wraps fields from a citation for entity broker.
	 * @author buckett
	 *
	 */
	public class DecoratedCitation {
		private String type;
		private Map<String,String> values;
		
		public DecoratedCitation(String type, Map<String,String> values) {
			this.type = type;
			this.values = values;
		}
		
		public String getType() {
			return type;
		}
		
		public Map<String,String> getValues() {
			return values;
		}
	}
	
	public class DecoratedCitationCollection {
		private String name;
		private String desctiption;
		private List<DecoratedCitation> citations;
		
		public DecoratedCitationCollection(String name, String description) {
			this.name = name;
			this.desctiption = description;
			this.citations = new ArrayList<DecoratedCitation>();
		}
		
		public void addCitation(DecoratedCitation citation) {
			citations.add(citation);
		}

		public String getName() {
			return name;
		}

		public String getDesctiption() {
			return desctiption;
		}

		public List<DecoratedCitation> getCitations() {
			return citations;
		}
	}

	public String[] getHandledOutputFormats() {
		return new String[]{JSON, FORM, HTML, XML, TXT};
	}

}
