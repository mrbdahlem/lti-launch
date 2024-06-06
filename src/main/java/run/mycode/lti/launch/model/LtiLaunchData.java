package run.mycode.lti.launch.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to hold the POST data from a Canvas LTI launch request. Yes, the method
 * names have underscores. I can't figure out how to make Spring MVC map a POST
 * parameter to a setter method that isn't identically named.
 */
@SuppressWarnings("unused")
public class LtiLaunchData {

    private static final Logger LOG = LoggerFactory.getLogger(LtiLaunchData.class);

    private String ltiVersion;
    private String contextLabel;
    private String resourceLinkId;
    private String lisPersonNameFamily;
    private Integer launchPresentationWidth;
    private String launchPresentationReturnUrl;
    private String toolConsumerInfoVersion;
    private String toolConsumerInstanceContactEmail;
    private String userId;
    private String ltiMessageType;
    private String toolConsumerInfoProductFamilyCode;
    private String toolConsumerInstanceGuid;
    private String userImage;
    private String launchPresentationDocumentTarget;
    private String contextTitle;
    private String toolConsumerInstanceName;
    private String lisPersonSourcedid;
    private String lisPersonNameFull;
    private Integer launchPresentationHeight;
    private String resourceLinkTitle;
    private String contextId;
    private String roles;
    private String lisPersonContactEmailPrimary;
    private String lisPersonNameGiven;
    private String launchPresentationLocale;
    private String lisResultSourcedid;
    private String lisOutcomeServiceUrl;


    private String extOutcomeDataValuesAccepted;
    
    // computed from a CSV list in the "roles" string
    private List<InstitutionRole> rolesList;

    // List of possible roles is from section A.2.2 at http://www.imsglobal.org/lti/blti/bltiv1p0/ltiBLTIimgv1p0.html
    // Going to be honest here. Pretty sure there needs to be some more intelligence added here. Especially with context sub-roles
    public enum InstitutionRole {
        Sysadmin, SysSupport, Creator, AccountAdmin, User, ContentDeveloper, Manager,
        Student, Faculty, Member, Learner, Instructor, TeachingAssistant, Mentor, Staff, Alumni,
        ProspectiveStudent,Guest, Other, Administrator, Observer, None;

        private static final Map<String, InstitutionRole> roleMap = new HashMap<>();

        static {
            //system roles
            roleMap.put("urn:lti:sysrole:ims/lis/SysAdmin", Sysadmin);
            roleMap.put("SysAdmin", Sysadmin);
            roleMap.put("urn:lti:sysrole:ims/lis/SysSupport", SysSupport);
            roleMap.put("SysSupport", SysSupport);
            roleMap.put("urn:lti:sysrole:ims/lis/Creator", Creator);
            roleMap.put("Creator", Creator);
            roleMap.put("urn:lti:sysrole:ims/lis/AccountAdmin", AccountAdmin);
            roleMap.put("AccountAdmin", AccountAdmin);
            roleMap.put("urn:lti:sysrole:ims/lis/User", User);
            roleMap.put("User", User);
            roleMap.put("urn:lti:sysrole:ims/lis/Administrator", Administrator);
            roleMap.put("Administrator", Administrator);
            roleMap.put("urn:lti:sysrole:ims/lis/None", None);
            roleMap.put("None", None);

            //institution roles
            roleMap.put("urn:lti:instrole:ims/lis/Student", Student);
            roleMap.put("Student", Student);
            roleMap.put("urn:lti:instrole:ims/lis/Faculty", Faculty);
            roleMap.put("Faculty", Faculty);
            roleMap.put("urn:lti:instrole:ims/lis/Member", Member);
            roleMap.put("Member", Member);
            roleMap.put("urn:lti:instrole:ims/lis/Learner", Learner);
            roleMap.put("Learner", Learner);
            roleMap.put("urn:lti:instrole:ims/lis/Instructor", Instructor);
            roleMap.put("Instructor", Instructor);
            roleMap.put("urn:lti:instrole:ims/lis/Mentor", Mentor);
            roleMap.put("Mentor", Mentor);
            roleMap.put("urn:lti:instrole:ims/lis/Staff", Staff);
            roleMap.put("Staff", Staff);
            roleMap.put("urn:lti:instrole:ims/lis/Alumni", Alumni);
            roleMap.put("Alumni", Alumni);
            roleMap.put("urn:lti:instrole:ims/lis/ProspectiveStudent",ProspectiveStudent);
            roleMap.put("ProspectiveStudent", ProspectiveStudent);
            roleMap.put("urn:lti:instrole:ims/lis/Guest", Guest);
            roleMap.put("Guest", Guest);
            roleMap.put("urn:lti:instrole:ims/lis/Other", Other);
            roleMap.put("Other", Other);
            roleMap.put("urn:lti:instrole:ims/lis/Administrator", Administrator);
            //short version of Administrator already done up above
            roleMap.put("urn:lti:instrole:ims/lis/Observer", Observer);
            roleMap.put("Observer", Observer);
            roleMap.put("urn:lti:instrole:ims/lis/None", None);
            //short version of None already done up above

            //context roles - does not include subroles yet
            roleMap.put("urn:lti:role:ims/lis/Learner", Learner);
            roleMap.put("urn:lti:role:ims/lis/Instructor", Instructor);
            roleMap.put("urn:lti:role:ims/lis/ContentDeveloper", ContentDeveloper);
            roleMap.put("ContentDeveloper", ContentDeveloper);
            roleMap.put("urn:lti:role:ims/lis/Member", Member);
            roleMap.put("urn:lti:role:ims/lis/Manager", Manager);
            roleMap.put("Manager", Manager);
            roleMap.put("urn:lti:role:ims/lis/Mentor", Mentor);
            roleMap.put("urn:lti:role:ims/lis/Administrator", Administrator);
            roleMap.put("urn:lti:role:ims/lis/TeachingAssistant", TeachingAssistant);
            roleMap.put("TeachingAssistant", TeachingAssistant);
        }

        public static InstitutionRole fromString(String roleStr) {
            InstitutionRole role = roleMap.get(roleStr);
            if (role == null) {
                throw new IllegalArgumentException(
                        "Unknown LTI Institution role string");
            }
            return role;
        }
    }

    public void setLti_version(String ltiVersion) {
        this.ltiVersion = ltiVersion;
    }

    public String getLti_version() {
        return ltiVersion;
    }

    public String getContext_label() {
        return contextLabel;
    }

    public void setContext_label(String contextLabel) {
        this.contextLabel = contextLabel;
    }

    public String getResource_link_id() {
        return resourceLinkId;
    }

    public void setResource_link_id(String resourceLinkId) {
        this.resourceLinkId = resourceLinkId;
    }

    public String getLis_person_name_family() {
        return lisPersonNameFamily;
    }

    public void setLis_person_name_family(String lisPersonNameFamily) {
        this.lisPersonNameFamily = lisPersonNameFamily;
    }

    public Integer getLaunch_presentation_width() {
        return launchPresentationWidth;
    }

    public void setLaunch_presentation_width(Integer launchPresentationWidth) {
        this.launchPresentationWidth = launchPresentationWidth;
    }

    public String getLaunch_presentation_return_url() {
        return launchPresentationReturnUrl;
    }

    public void setLaunch_presentation_return_url(String launchPresentationReturnUrl) {
        this.launchPresentationReturnUrl = launchPresentationReturnUrl;
    }

    public String getTool_consumer_info_version() {
        return toolConsumerInfoVersion;
    }

    public void setTool_consumer_info_version(String toolConsumerInfoVersion) {
        this.toolConsumerInfoVersion = toolConsumerInfoVersion;
    }

    public String getTool_consumer_instance_contact_email() {
        return toolConsumerInstanceContactEmail;
    }

    public void setTool_consumer_instance_contact_email(String toolConsumerInstanceContactEmail) {
        this.toolConsumerInstanceContactEmail = toolConsumerInstanceContactEmail;
    }

    public String getUser_id() {
        return userId;
    }

    public void setUser_id(String userId) {
        this.userId = userId;
    }
    
    public String getLti_message_type() {
        return ltiMessageType;
    }

    public void setLti_message_type(String ltiMessageType) {
        this.ltiMessageType = ltiMessageType;
    }

    public String getTool_consumer_info_product_family_code() {
        return toolConsumerInfoProductFamilyCode;
    }

    public void setTool_consumer_info_product_family_code(String toolConsumerInfoProductFamilyCode) {
        this.toolConsumerInfoProductFamilyCode = toolConsumerInfoProductFamilyCode;
    }

    public String getTool_consumer_instance_guid() {
        return toolConsumerInstanceGuid;
    }

    public void setTool_consumer_instance_guid(String toolConsumerInstanceGuid) {
        this.toolConsumerInstanceGuid = toolConsumerInstanceGuid;
    }

    public String getUser_image() {
        return userImage;
    }

    public void setUser_image(String userImage) {
        this.userImage = userImage;
    }

    public String getLaunch_presentation_document_target() {
        return launchPresentationDocumentTarget;
    }

    public void setLaunch_presentation_document_target(String launchPresentationDocumentTarget) {
        this.launchPresentationDocumentTarget = launchPresentationDocumentTarget;
    }

    public String getContext_title() {
        return contextTitle;
    }

    public void setContext_title(String contextTitle) {
        this.contextTitle = contextTitle;
    }

    public String getTool_consumer_instance_name() {
        return toolConsumerInstanceName;
    }

    public void setTool_consumer_instance_name(String toolConsumerInstanceName) {
        this.toolConsumerInstanceName = toolConsumerInstanceName;
    }

    public String getLis_person_sourcedid() {
        return lisPersonSourcedid;
    }

    public void setLis_person_sourcedid(String lisPersonSourcedid) {
        this.lisPersonSourcedid = lisPersonSourcedid;
    }

    public String getLis_person_name_full() {
        return lisPersonNameFull;
    }

    public void setLis_person_name_full(String lisPersonNameFull) {
        this.lisPersonNameFull = lisPersonNameFull;
    }

    public Integer getLaunch_presentation_height() {
        return launchPresentationHeight;
    }

    public void setLaunch_presentation_height(Integer launchPresentationHeight) {
        this.launchPresentationHeight = launchPresentationHeight;
    }

    public String getResource_link_title() {
        return resourceLinkTitle;
    }

    public void setResource_link_title(String resourceLinkTitle) {
        this.resourceLinkTitle = resourceLinkTitle;
    }
    
    public String getLis_result_sourcedid() {
        return lisResultSourcedid;
    }

    public void setLis_result_sourcedid(String lisResultSourcedid) {
        this.lisResultSourcedid = lisResultSourcedid;
    }

    public String getLis_outcome_service_url() {
        return lisOutcomeServiceUrl;
    }

    public void setLis_outcome_service_url(String lisOutcomeServiceUrl) {
        this.lisOutcomeServiceUrl = lisOutcomeServiceUrl;
    }
    
    public String getContext_id() {
        return contextId;
    }

    public void setContext_id(String contextId) {
        this.contextId = contextId;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        if (roles == null) {
            roles = "";
        }

        LOG.debug("got LTI roles: {}", roles);
        this.roles = roles;
        
        List<InstitutionRole> list = new ArrayList<>();
        String[] splitRoles = roles.trim().split(",");
        for (String splitRole : splitRoles) {
            list.add(InstitutionRole.fromString(splitRole));
        }
        this.rolesList = list;
    }

    public List<InstitutionRole> getRolesList() {
        return rolesList;
    }

    public String getLis_person_contact_email_primary() {
        return lisPersonContactEmailPrimary;
    }

    public void setLis_person_contact_email_primary(String lisPersonContactEmailPrimary) {
        this.lisPersonContactEmailPrimary = lisPersonContactEmailPrimary;
    }

    public String getLis_person_name_given() {
        return lisPersonNameGiven;
    }

    public void setLis_person_name_given(String lisPersonNameGiven) {
        this.lisPersonNameGiven = lisPersonNameGiven;
    }

    public String getLaunch_presentation_locale() {
        return launchPresentationLocale;
    }

    public void setLaunch_presentation_locale(String launchPresentationLocale) {
        this.launchPresentationLocale = launchPresentationLocale;
    }

    public String getExt_outcome_data_values_accepted() {
        return extOutcomeDataValuesAccepted;
    }

    public void setExt_outcome_data_values_accepted(String extOutcomeDataValuesAccepted) {
        this.extOutcomeDataValuesAccepted = extOutcomeDataValuesAccepted;
    }
}
