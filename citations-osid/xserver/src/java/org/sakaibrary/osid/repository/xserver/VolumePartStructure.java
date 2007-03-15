package org.sakaibrary.osid.repository.xserver;

/*******************************************************************************
 * Copyright (c) 2003, 2004, 2005 The Regents of the University of Michigan,
 * Trustees of Indiana University, Board of Trustees of the Leland Stanford,
 * Jr., University, and The MIT Corporation
 *
 * Licensed under the Educational Community License Version 1.0 (the "License");
 * By obtaining, using and/or copying this Original Work, you agree that you
 * have read, understand, and will comply with the terms and conditions of the
 * Educational Community License.  You may obtain a copy of the License at:
 *
 *      http://cvs.sakaiproject.org/licenses/license_1_0.html
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
*******************************************************************************/

public class VolumePartStructure implements org.osid.repository.PartStructure
{
	private static final org.apache.commons.logging.Log LOG =
		org.apache.commons.logging.LogFactory.getLog(
				"org.sakaibrary.osid.repository.xserver.VolumePartStructure" );

    private org.osid.shared.Id VOLUME_PART_STRUCTURE_ID = null;
    private org.osid.shared.Type type = new Type( "sakaibrary", "partStructure",
    		"volume", "Volume number of source" );
    private String displayName = "Volume";
    private String description = "Volume number of source (i.e. Journal title" +
    		", vol. 23)";
    private boolean mandatory = false;
    private boolean populatedByRepository = false;
    private boolean repeatable = false;
    
	private static VolumePartStructure volumePartStructure;

    private VolumePartStructure()
    {
        try
        {
            this.VOLUME_PART_STRUCTURE_ID = Managers.getIdManager().getId(
            		"011293y7h98332do29873e1v000z02");
        }
        catch (Throwable t)
        {
        	LOG.warn( "VolumePartStructure() failed to get partStructure id: "
					+ t.getMessage() );
        }
    }
    
	protected static synchronized VolumePartStructure getInstance()
	{
		if( volumePartStructure == null ) {
			volumePartStructure = new VolumePartStructure();
		}
		return volumePartStructure;
	}
	
    public String getDisplayName()
    throws org.osid.repository.RepositoryException
    {
        return this.displayName;
    }

    public String getDescription()
    throws org.osid.repository.RepositoryException
    {
        return this.description;
    }

    public boolean isMandatory()
    throws org.osid.repository.RepositoryException
    {
        return this.mandatory;
    }

    public boolean isPopulatedByRepository()
    throws org.osid.repository.RepositoryException
    {
        return this.populatedByRepository;
    }

    public boolean isRepeatable()
    throws org.osid.repository.RepositoryException
    {
        return this.repeatable;
    }


    public void updateDisplayName(String displayName)
    throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.shared.Id getId()
    throws org.osid.repository.RepositoryException
    {
        return this.VOLUME_PART_STRUCTURE_ID;
    }

    public org.osid.shared.Type getType()
    throws org.osid.repository.RepositoryException
    {
        return this.type;
    }

    public org.osid.repository.RecordStructure getRecordStructure()
    throws org.osid.repository.RepositoryException
    {
        return RecordStructure.getInstance();
    }

    public boolean validatePart(org.osid.repository.Part part)
    throws org.osid.repository.RepositoryException
    {
        return true;
    }

    public org.osid.repository.PartStructureIterator getPartStructures()
    throws org.osid.repository.RepositoryException
    {
        return new PartStructureIterator(new java.util.Vector());
    }
}