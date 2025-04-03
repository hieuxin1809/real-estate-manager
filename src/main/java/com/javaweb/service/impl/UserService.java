package com.javaweb.service.impl;

import com.javaweb.constant.SystemConstant;
import com.javaweb.converter.UserConverter;
import com.javaweb.entity.*;
import com.javaweb.model.dto.PasswordDTO;
import com.javaweb.model.dto.UserDTO;
import com.javaweb.exception.ServiceException;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.repository.*;
import com.javaweb.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserConverter userConverter;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AssignmentCustomerRepository assignmentCustomerRepository;

    @Override
    public UserDTO findOneByUserNameAndStatus(String name, int status) {
        UserEntity userEntity = userRepository.findOneByUserNameAndStatus(name, status);
        if(userEntity == null) {
            throw new UsernameNotFoundException("not found " + name);
        }
        return userConverter.convertToDto(userEntity);
    }

    @Override
    public List<UserDTO> getUsers(String searchValue, Pageable pageable) {
        Page<UserEntity> users = null;
        if (StringUtils.isNotBlank(searchValue)) {
            users = userRepository.findByUserNameContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatusNot(searchValue, searchValue, 0, pageable);
        } else {
            users = userRepository.findByStatusNot(0, pageable);
        }
        List<UserEntity> newsEntities = users.getContent();
        List<UserDTO> result = new ArrayList<>();
        for (UserEntity userEntity : newsEntities) {
            UserDTO userDTO = userConverter.convertToDto(userEntity);
            userDTO.setRoleCode(userEntity.getRoles().get(0).getCode());
            result.add(userDTO);
        }
        return result;
    }



    @Override
    public List<UserDTO> getAllUsers(Pageable pageable) {
        List<UserEntity> userEntities = userRepository.getAllUsers(pageable);
        List<UserDTO> results = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            UserDTO userDTO = userConverter.convertToDto(userEntity);
            userDTO.setRoleCode(userEntity.getRoles().get(0).getCode());
            results.add(userDTO);
        }
        return results;
    }

    @Override
    public int countTotalItems() {
        return userRepository.countTotalItem();
    }

    @Override
    public Map<Long, String> getListStaff() {
        List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(1 , "STAFF");
        Map<Long, String> staffList = new LinkedHashMap<>();
        for (UserEntity userEntity : staffs) {
            staffList.put(userEntity.getId(), userEntity.getFullName());
        }
        return staffList;
    }

    @Override
    public List<StaffResponseDTO> LoadStaffBuilding(Long id) {
        // lay tat ca nhan vien co giao va ko giao
        List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(1 , "STAFF");
        BuildingEntity buildingEntity = buildingRepository.findById(id).get();
        // lay danh sach user dang quan ly toa nha co id = buildingid theo bang quan he;
        List<UserEntity> assignStaff = userRepository.findByAssignmentBuildingEntities_BuildingEntity_Id(id);
        List<StaffResponseDTO> staffResponseDTOS = new ArrayList<>();
        for(UserEntity staff : staffs) {
            StaffResponseDTO staffResponseDTO = new StaffResponseDTO();
            staffResponseDTO.setStaffId(staff.getId());
            staffResponseDTO.setFullName(staff.getFullName());
            if(assignStaff.contains(staff)){
                staffResponseDTO.setChecked("checked");
            }
            else staffResponseDTO.setChecked("");
            staffResponseDTOS.add(staffResponseDTO);
        }
        return staffResponseDTOS;
    }
    @Override
    public List<StaffResponseDTO> LoadStaffCustomer(Long id) {
        List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(1 , "STAFF");
        CustomerEntity customerEntity = customerRepository.findById(id).get();
        List<StaffResponseDTO> staffResponseDTOS = new ArrayList<>();
        List<UserEntity> assignStaff = userRepository.findByAssignmentCustomerEntities_Customer_Id(id);
        for(UserEntity staff : staffs) {
            StaffResponseDTO staffResponseDTO = new StaffResponseDTO();
            staffResponseDTO.setStaffId(staff.getId());
            staffResponseDTO.setFullName(staff.getFullName());
            if(assignStaff.contains(staff)){
                staffResponseDTO.setChecked("checked");
            }
            else staffResponseDTO.setChecked("");
            staffResponseDTOS.add(staffResponseDTO);
        }
        return staffResponseDTOS;
    }
    @Override
    public int getTotalItems(String searchValue) {
        int totalItem = 0;
        if (StringUtils.isNotBlank(searchValue)) {
            totalItem = (int) userRepository.countByUserNameContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatusNot(searchValue, searchValue, 0);
        } else {
            totalItem = (int) userRepository.countByStatusNot(0);
        }
        return totalItem;
    }

    @Override
    public UserDTO findOneByUserName(String userName) {
        UserEntity userEntity = userRepository.findOneByUserName(userName);
        UserDTO userDTO = userConverter.convertToDto(userEntity);
        return userDTO;
    }

    @Override
    public UserDTO findUserById(long id) {
        UserEntity entity = userRepository.findById(id).get();
        List<RoleEntity> roles = entity.getRoles();
        UserDTO dto = userConverter.convertToDto(entity);
        roles.forEach(item -> {
            dto.setRoleCode(item.getCode());
        });
        return dto;
    }

    @Override
    @Transactional
    public UserDTO insert(UserDTO newUser) {
        RoleEntity role = roleRepository.findOneByCode(newUser.getRoleCode());
        UserEntity userEntity = userConverter.convertToEntity(newUser);
        userEntity.setRoles(Stream.of(role).collect(Collectors.toList()));
        userEntity.setStatus(1);
        if(newUser.getPassword() == null){
            userEntity.setPassword(passwordEncoder.encode(SystemConstant.PASSWORD_DEFAULT));
        }
        else{
            userEntity.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
        return userConverter.convertToDto(userRepository.save(userEntity));
    }

    @Override
    @Transactional
    public UserDTO update(Long id, UserDTO updateUser) {
        RoleEntity role = roleRepository.findOneByCode(updateUser.getRoleCode());
        UserEntity oldUser = userRepository.findById(id).get();
        UserEntity userEntity = userConverter.convertToEntity(updateUser);
        userEntity.setUserName(oldUser.getUserName());
        userEntity.setStatus(oldUser.getStatus());
        userEntity.setRoles(Stream.of(role).collect(Collectors.toList()));
        userEntity.setPassword(oldUser.getPassword());
        return userConverter.convertToDto(userRepository.save(userEntity));
    }

    @Override
    @Transactional
    public void updatePassword(long id, PasswordDTO passwordDTO) throws ServiceException {
        UserEntity user = userRepository.findById(id).get();
        if (passwordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword())
                && passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new ServiceException(SystemConstant.CHANGE_PASSWORD_FAIL);
        }
    }

    @Override
    @Transactional
    public UserDTO resetPassword(long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        userEntity.setPassword(passwordEncoder.encode(SystemConstant.PASSWORD_DEFAULT));
        return userConverter.convertToDto(userRepository.save(userEntity));
    }

    @Override
    @Transactional
    public UserDTO updateProfileOfUser(String username, UserDTO updateUser) {
        UserEntity oldUser = userRepository.findOneByUserName(username);
        oldUser.setFullName(updateUser.getFullName());
        return userConverter.convertToDto(userRepository.save(oldUser));
    }

    @Override
    @Transactional
    public void delete(long[] ids) {
        for (Long item : ids) {
            UserEntity userEntity = userRepository.findById(item).get();
            userEntity.setStatus(0);
            userRepository.save(userEntity);
        }
    }
}
