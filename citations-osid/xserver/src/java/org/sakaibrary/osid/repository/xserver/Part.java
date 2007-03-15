package org.sakaibrary.osid.repository.xserver;

/**********************************************************************************
 * $URL$
 * $Id$
 **********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005 The Regents of the University of Michigan, Trustees of Indiana University,
 *                  Board of Trustees of the Leland Stanford, Jr., University, and The MIT Corporation
 *
 * Licensed under the Educational Community License Version 1.0 (the "License");
 * By obtaining, using and/or copying this Original Work, you agree that you have read,
 * understand, and will comply with the terms and conditions of the Educational Community License.
 * You may obtain a copy of the License at:
 *
 *      http://cvs.sakaiproject.org/licenses/license_1_0.html
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 **********************************************************************************/
/**
 * @author Massachusetts Institute of Techbology, Sakai Software Development Team
 * @version
 */
public class Part
implements org.osid.repository.Part
{
	private static final org.apache.commons.logging.Log LOG =
		org.apache.commons.logging.LogFactory.getLog(
				"org.sakaibrary.osid.repository.xserver.Part" );
	
    private org.osid.repository.PartStructure partStructure = null;
    private org.osid.shared.Id partStructureId = null;
    private java.io.Serializable value = null;
    private org.osid.id.IdManager idManager = null;
    private String displayName = null;
    private org.osid.shared.Id id = null;

    public String getDisplayName()
    throws org.osid.repository.RepositoryException
    {
        return this.displayName;
    }

    public org.osid.shared.Id getId()
    throws org.osid.repository.RepositoryException
    {
        return this.id;
    }

    protected Part(org.osid.shared.Id partStructureId
                 , java.io.Serializable value
                 , org.osid.id.IdManager idManager)
    throws org.osid.repository.RepositoryException
    {
        this.idManager = idManager;
        this.partStructureId = partStructureId;
        this.value = value;
        
        try
        {
            this.id = this.idManager.createId();
			if (partStructureId.isEqual(DateRetrievedPartStructure.getInstance().getId())) {
				this.partStructure = DateRetrievedPartStructure.getInstance();
			} else if (partStructureId.isEqual(EditionPartStructure.getInstance().getId())) {
				this.partStructure = EditionPartStructure.getInstance();
			} else if (partStructureId.isEqual(CreatorPartStructure.getInstance().getId())) {
				this.partStructure = CreatorPartStructure.getInstance();
			} else if (partStructureId.isEqual(DatePartStructure.getInstance().getId())) {
				this.partStructure = DatePartStructure.getInstance();
			} else if (partStructureId.isEqual(DOIPartStructure.getInstance().getId())) {
				this.partStructure = DOIPartStructure.getInstance();
			} else if (partStructureId.isEqual(InLineCitationPartStructure.getInstance().getId())) {
				this.partStructure = InLineCitationPartStructure.getInstance();
			} else if (partStructureId.isEqual(LanguagePartStructure.getInstance().getId())) {
				this.partStructure = LanguagePartStructure.getInstance();
			} else if (partStructureId.isEqual(PublisherPartStructure.getInstance().getId())) {
				this.partStructure = PublisherPartStructure.getInstance();
			} else if (partStructureId.isEqual(IssuePartStructure.getInstance().getId())) {
				this.partStructure = IssuePartStructure.getInstance();
			} else if (partStructureId.isEqual(LocIdentifierPartStructure.getInstance().getId())) {
				this.partStructure = LocIdentifierPartStructure.getInstance();
			} else if (partStructureId.isEqual(SourceTitlePartStructure.getInstance().getId())) {
				this.partStructure = SourceTitlePartStructure.getInstance();
			} else if (partStructureId.isEqual(SubjectPartStructure.getInstance().getId())) {
				this.partStructure = SubjectPartStructure.getInstance();
			} else if (partStructureId.isEqual(TypePartStructure.getInstance().getId())) {
				this.partStructure = TypePartStructure.getInstance();
			} else if (partStructureId.isEqual(NotePartStructure.getInstance().getId())) {
				this.partStructure = NotePartStructure.getInstance();
			} else if (partStructureId.isEqual(IsnIdentifierPartStructure.getInstance().getId())) {
				this.partStructure = IsnIdentifierPartStructure.getInstance();
			} else if (partStructureId.isEqual(URLPartStructure.getInstance().getId())) {
				this.partStructure = URLPartStructure.getInstance();
			} else if (partStructureId.isEqual(URLLabelPartStructure.getInstance().getId())) {
				this.partStructure = URLLabelPartStructure.getInstance();
			} else if (partStructureId.isEqual(URLFormatPartStructure.getInstance().getId())) {
				this.partStructure = URLFormatPartStructure.getInstance();
			} else if (partStructureId.isEqual(OpenUrlPartStructure.getInstance().getId())) {
				this.partStructure = OpenUrlPartStructure.getInstance();
			} else if (partStructureId.isEqual(PagesPartStructure.getInstance().getId())) {
				this.partStructure = PagesPartStructure.getInstance();
			} else if (partStructureId.isEqual(PublicationLocationPartStructure.getInstance().getId())) {
				this.partStructure = PublicationLocationPartStructure.getInstance();
			} else if (partStructureId.isEqual(RightsPartStructure.getInstance().getId())) {
				this.partStructure = RightsPartStructure.getInstance();
			} else if (partStructureId.isEqual(VolumePartStructure.getInstance().getId())) {
				this.partStructure = VolumePartStructure.getInstance();
			} else if (partStructureId.isEqual(YearPartStructure.getInstance().getId())) {
				this.partStructure = YearPartStructure.getInstance();
			} else if (partStructureId.isEqual(StartPagePartStructure.getInstance().getId())) {
				this.partStructure = StartPagePartStructure.getInstance();
			} else if (partStructureId.isEqual(EndPagePartStructure.getInstance().getId())) {
				this.partStructure = EndPagePartStructure.getInstance();
			}
			
			this.displayName = this.partStructure.getDisplayName();
        }
        catch (Throwable t)
        {
            LOG.warn(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.repository.RepositoryException.OPERATION_FAILED);
        }
    }

    public org.osid.repository.Part createPart(org.osid.shared.Id partStructureId
                                             , java.io.Serializable value)
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public void deletePart(org.osid.shared.Id partStructureId)
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public void updateDisplayName(String displayName)
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.repository.PartIterator getParts()
    throws org.osid.repository.RepositoryException
    {
        return new PartIterator(new java.util.Vector());
    }

    public org.osid.repository.PartStructure getPartStructure()
    throws org.osid.repository.RepositoryException
    {
		return this.partStructure;
    }

    public java.io.Serializable getValue()
    throws org.osid.repository.RepositoryException
    {
        return this.value;
    }

    public void updateValue(java.io.Serializable value)
    throws org.osid.repository.RepositoryException
    {
        this.value = value;
    }
}