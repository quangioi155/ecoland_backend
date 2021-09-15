package com.ecoland.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecoland.common.CommonService;
import com.ecoland.common.Constants;
import com.ecoland.entity.Branches;
import com.ecoland.entity.Partners;
import com.ecoland.entity.UserAccounts;
import com.ecoland.entity.UserGroups;
import com.ecoland.exception.RecordNotFoundException;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.EditOrCreateUserRequest;
import com.ecoland.model.request.LoginRequest;
import com.ecoland.model.request.UserAccountListRequest;
import com.ecoland.model.response.DetailUserAccountsResponse;
import com.ecoland.model.response.LoginResponse;
import com.ecoland.repository.CommonDAO;
import com.ecoland.repository.UserAccountsRepository;
import com.ecoland.repository.dao.UserAccountListDAO;
import com.ecoland.security.UserDetailsImpl;
import com.ecoland.security.jwt.JwtTokenUtil;
import com.ecoland.utils.Utils;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Service
@Transactional
public class UserAccountsService {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountsService.class);

    @Autowired
    private UserAccountsRepository userRepository;

    @Autowired
    private UserAccountListDAO accountListDAO;

    @Autowired
    private BranchesService branchesService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserGroupsService userGroupsService;

//    @Autowired
//    private PasswordEncoder encoder;

    @Autowired
    private JwtTokenUtil jwtUtils;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    private CommonDAO commonDAO;

    /**
     * 
     * @author thaotv@its-global.vn This is method login
     * @param request
     * @return loginResponse
     * 
     */
    @SuppressWarnings("static-access")
    public LoginResponse login(LoginRequest request) {
        logger.info("Login user with user and password");
        UserDetailsImpl userDetails = null;
        List<String> roles = null;
        String jwt = null;
        Optional<UserAccounts> opionalUserAccount = findByUser(request.getUsername().trim());
        try {
//	    Authentication authentication = authenticationManager.authenticate(
//		    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
//	    SecurityContextHolder.getContext().setAuthentication(authentication);

//	    jwt = jwtUtils.generateJwtToken(authentication);

            if (opionalUserAccount.isEmpty()) {
                return null;
            } else {
                String password = Utils.decrypt(opionalUserAccount.get().getLoginPassword(), Constants.SECRET);
                if (!password.equals(request.getPassword())) {
                    return null;
                }
                jwt = jwtUtils.generateJwtToken(opionalUserAccount.get().getLoginId());
                roles = userDetails.build(opionalUserAccount.get()).getAuthorities().stream()
                        .map(item -> item.getAuthority()).collect(Collectors.toList());
            }
        } catch (Exception e) {
            logger.info("Error login: " + e.getMessage());
        }
        return new LoginResponse(opionalUserAccount.get().getLoginId(), jwt, roles);
    }

    @CacheEvict(value = "finByLoginId", allEntries = true)
    public Optional<UserAccounts> findByUser(String loginId) {
        return userRepository.findByLoginIdAndDeleteFlag(loginId.trim(), 0);
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param request
     * @return list user account
     * 
     */
    @CacheEvict(value = "userAccountListSearch", allEntries = true)
    public ResultPageResponse userAccountList(UserAccountListRequest request) {
        logger.info("-- Get user list --");
        return accountListDAO.listUserAccount(request);
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param id
     * @return update delete flag = 1
     * 
     */
    public void deleteUserAccount(Long id) {
        logger.info("-- Delete user account by id --");
        Optional<UserAccounts> userAccounts = userRepository.findByIdAndDeleteFlag(id, 0);
        if (userAccounts.isEmpty()) {
            throw new RecordNotFoundException("Not Found");
        }
        commonDAO.sqlCommonDAO(Constants.USER_ACCOUNTS, id, Constants.DELETE, commonService.idUserAccountLogin());
    }

    public Optional<UserAccounts> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param id
     * @return DetailUserAccountsResponse
     * 
     */
    public DetailUserAccountsResponse detailUserAccountsById(Long id) {
        logger.info("-- Detail user account by id --");
        Optional<UserAccounts> optionalUserAccounts = findById(id);
        DetailUserAccountsResponse detail = new DetailUserAccountsResponse();
        if (!optionalUserAccounts.isEmpty()) {
            detail.setId(optionalUserAccounts.get().getId());
            detail.setLoginId(optionalUserAccounts.get().getLoginId());
            detail.setLoginPassword(Utils.decrypt(optionalUserAccounts.get().getLoginPassword(), Constants.SECRET));
            detail.setAccountName(optionalUserAccounts.get().getAccountName());
            detail.setAccountNameKana(optionalUserAccounts.get().getAccountNameKana());
            detail.setBranchId(optionalUserAccounts.get().getBranches().getId());
            detail.setDescription(optionalUserAccounts.get().getDescription());
            detail.setEmployeeCd(optionalUserAccounts.get().getEmployeeCd());
            detail.setPartnerId(optionalUserAccounts.get().getPartners().getId());
            detail.setUserGroupId(optionalUserAccounts.get().getUserGroups().getId());
        }
        return detail;
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param request
     * 
     */
    public ApiResponse editUserAccounts(EditOrCreateUserRequest request) throws Exception {
        logger.info("-- Edit user accounts --");
        Optional<UserAccounts> optionalUserAccounts = findById(request.getId());
        if (optionalUserAccounts.isEmpty()) {
            logger.error(Constants.RECORD_DOES_NOT_EXIST);
            throw new Exception(Constants.RECORD_DOES_NOT_EXIST);
        } else {
            Optional<UserAccounts> userAccounts = userRepository.findByLoginId(request.getLoginId().trim());
            if (!userAccounts.isEmpty() && userAccounts.get().getId().compareTo(request.getId()) != 0) {
                logger.error(Constants.RECORD_ALREADY_EXISTS);
                return new ApiResponse(Constants.HTTP_CODE_403, Constants.RECORD_ALREADY_EXISTS, null);
            }
            UserAccounts uAccounts = optionalUserAccounts.get();
            Optional<UserGroups> optionalUserGroups = userGroupsService
                    .findByIdAndDeleteFlagUserGroups(request.getUserGroupId(), 0);
            Optional<Branches> optionalBranches = branchesService.findByIdAndDeleteFlagBranches(request.getBranchId(),
                    0);
            Optional<Partners> optionalPartners = partnerService.findByIdAndDeleteFlagPartners(request.getPartnerId(),
                    0);
            if (optionalUserGroups.isEmpty() || optionalBranches.isEmpty() || optionalPartners.isEmpty()) {
                logger.error(Constants.RECORD_DOES_NOT_EXIST);
                throw new Exception(Constants.RECORD_DOES_NOT_EXIST);
            }
            if (optionalUserAccounts.get().getBranches().getId().compareTo(optionalBranches.get().getId()) != 0) {
                uAccounts.setBranches(optionalBranches.get());
            }
            if (optionalUserAccounts.get().getPartners().getId().compareTo(optionalPartners.get().getId()) != 0) {
                uAccounts.setPartners(optionalPartners.get());
            }
            if (optionalUserAccounts.get().getUserGroups().getId().compareTo(optionalUserGroups.get().getId()) != 0) {
                uAccounts.setUserGroups(optionalUserGroups.get());
            }
            setUserAccounts(request, uAccounts);
            userRepository.save(uAccounts);
            commonDAO.sqlCommonDAO(Constants.USER_ACCOUNTS, request.getId(), Constants.UPDATE,
                    commonService.idUserAccountLogin());
            return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
        }
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param request
     * 
     */
    public ApiResponse createUserAccounts(EditOrCreateUserRequest request) throws Exception {
        logger.info("-- Create user accounts --");
        Optional<UserAccounts> lstUserAccounts = userRepository.findByLoginId(request.getLoginId().trim());
        Integer idLogin = commonService.idUserAccountLogin();
        if (!lstUserAccounts.isEmpty()) {
            logger.error(Constants.RECORD_ALREADY_EXISTS);
            return new ApiResponse(Constants.HTTP_CODE_400, Constants.CREATE_SUCCESS, null);
        } else {
            Optional<UserGroups> optionalUserGroups = userGroupsService
                    .findByIdAndDeleteFlagUserGroups(request.getUserGroupId(), 0);
            Optional<Branches> optionalBranches = branchesService.findByIdAndDeleteFlagBranches(request.getBranchId(),
                    0);
            Optional<Partners> optionalPartners = partnerService.findByIdAndDeleteFlagPartners(request.getPartnerId(),
                    0);
            if (optionalUserGroups.isEmpty() || optionalBranches.isEmpty() || optionalPartners.isEmpty()) {
                logger.error(Constants.RECORD_DOES_NOT_EXIST);
                throw new Exception(Constants.RECORD_DOES_NOT_EXIST);
            } else {
                UserAccounts userAccounts = new UserAccounts();
                setUserAccounts(request, userAccounts);
                userAccounts.setPartners(optionalPartners.get());
                userAccounts.setBranches(optionalBranches.get());
                userAccounts.setUserGroups(optionalUserGroups.get());
                userAccounts.setCreatedBy(idLogin);
                userAccounts.setUpdatedBy(idLogin);
                userAccounts.setDeleteFlag(0);
                userAccounts.setCreateAt(new Timestamp(new Date().getTime()));
                userAccounts.setUpdatedAt(new Timestamp(new Date().getTime()));
                userRepository.save(userAccounts);
                return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
            }
        }
    }

    public UserAccounts setUserAccounts(EditOrCreateUserRequest request, UserAccounts userAccounts) {
        userAccounts.setLoginId(request.getLoginId());
        userAccounts.setAccountName(request.getAccountName().trim());
        userAccounts.setAccountNameKana(request.getAccountNameKana().trim());
        userAccounts.setEmployeeCd(request.getEmployeeCd() != null ? request.getEmployeeCd().trim() : null);
        userAccounts.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
        userAccounts.setLoginPassword(Utils.encrypt(request.getLoginPassword(), Constants.SECRET));
        return userAccounts;
    }
}
