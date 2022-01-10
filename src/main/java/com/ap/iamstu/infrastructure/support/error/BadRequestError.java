package com.ap.iamstu.infrastructure.support.error;

import lombok.Getter;

@Getter
public enum BadRequestError implements ResponseError {
    INVALID_INPUT(4000000, "Invalid input : {0}"),
    INVALID_ACCEPT_LANGUAGE(4000001, "Invalid value for request header Accept-Language: {0}"),
    MISSING_PATH_VARIABLE(4000002, "Missing path variable"), //MissingPathVariable
    PATH_INVALID(4000003, "Path is invalid"),
    UNDEFINED(4000000, ""),
    USER_EMAIL_EXITED(40001001, "Email exited"),
    USER_USERNAME_EXITED(40001002, "Username exited"),
    USER_CAN_NOT_PERFORM_THIS_ACTION(40001003, "Account cannot perform this action"),
    WRONG_PASSWORD(40001004, "Wrong password"),
    USER_INVALID(40001005, "User invalid: {0}"),
    STATUS_INVALID(40001006, "Status invalid: {0}"),
    SYNC_VIDEO_IN_CHANNEL_ERROR(40001007, "Sync video in channel error"),
    DEPARTMENT_CODE_EXISTED(40001008, "DepartmentCode exited"),
    DEPARTMENT_NOT_EXITED(40001009, "Department not exited"),
    ORG_UNIT_NOT_EXITED(40001010, "OrgUnit not exited"),
    ORG_UNIT_CODE_EXITED(40001011, "OrgUnit Code exited"),
    ORG_UNIT_CODE_NOT_EXITED(40001012, "OrgUnit Code not exited"),
    IDS_IS_REQUIRED(40001001, "Ids is required"),
    LOGIN_FAIL_BLOCK_ACCOUNT(40001013, "Login fail due account was block!"),
    LOGIN_FAIL_WARNING_BEFORE_BLOCK(40001014, "Warning account will be block!"),
    PASSWORD_REQUIRED(40001015, "Password is required"),
    CHANGE_PASSWORD_NOT_SUPPORTED(40001016, "Change password is supported"),
    USER_LDAP_NOT_EXISTED(40001017, "User LDAP not existed"),
    CLIENT_NOT_EXISTED(4001018, "Client not exited"),
    ORGANIZATION_CODE_EXITED(4001019, "Organization code exited"),
    PASSWORD_NOT_STRONG(4001020, "Password required strong"),
    ORGANIZATION_LEASING_UNIT_NOT_EXITED(4001021, "Organization not leasing this unit"),
    BUILDING_IS_NOT_REGISTERED_ORGANIZATION(4001022, "Building is not registered organization"),
    USER_NOT_PERMISSION_ON_BUILDINGS(4001023, "User not permission on the buildings"),
    UNIT_IN_USE(4001024, "Unit is in use"),
    CONTACT_ORGANIZATION_NOT_EXISTED(4001025, "Contact Organization not existed"),
    EMAIL_WAS_USED_IN_ORGANIZATION(4001026, "Email was used in organization"),
    PHONE_NUMBER_WAS_USED_IN_ORGANIZATION(4001027, "Email was used in organization"),
    USER_PHONE_NUMBER_EXITED(4001028, "Phone number exited"),
    USER_EMPLOYEE_CODE_EXITED(4001029, "Employee code exited"),
    CLIENT_NAME_EXITED(4001030, "Client name exited"),
    CANNOT_DELETE_ACTIVE_USER(4001031, "Can not delete because user is active"),
    CANNOT_DELETE_DELETED_USER(4001032, "User has been deleted"),
    EMAIL_NOT_EXISTED_IN_SYSTEM(4001033, "Email not existed in system"),
    REPEAT_PASSWORD_DOES_NOT_MATCH(4001034, "Repeat password does not match"),
    ROLE_INVALID(4001035, "Role is invalid"),
    FLOOR_NOT_FOUND(4001036, "Floor not found"),
    IDS_INVALID(4001037, "Ids invalid"),
    CAN_NOT_UPDATE_BUILDING_FOR_THIS_ACCOUNT(4001038, "Can not update building for this account!"),
    CAN_NOT_UPDATE_ORGANIZATION_FOR_THIS_ACCOUNT(4001039, "Can not update organization for this account!"),
    ADMIN_MUST_CENTER_LEVEL(4001040, "Role admin must be center level"),
    BUILDING_ID_REQUIRED(4001041, "Building id required"),
    ACCOUNT_NOT_PERMISSION_ON_WEB(4001042, "Account not permission on web"),
    ORGANIZATION_CAN_NOT_INACTIVE_DUE_HAVE_GROUND_USING(4001043, "Organization can not inactive due have ground using"),
    ORGANIZATION_CAN_NOT_INACTIVE_DUE_RENTING_SPACE_ANOTHER_BUILDING(4001044, "Organization can not inactive due renting space another building"),
    LOGIN_FAIL_BECAUSE_YOUR_ORGANIZATION_WAS_BLOCK(4001045, "Login fail due your organization was block"),
    BUSINESS_CODE_EXISTED(4001046, "Business code exited"),
    FILE_NOT_EXISTED(4001047, "Avatar file not existed"),
    ACCOUNT_EMPLOYEE_CAN_NOT_CHANGE_PASSWORD(4001048, "Account employee can not execute the function changing password")
    ;

    private final Integer code;
    private final String message;

    BadRequestError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return 400;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
