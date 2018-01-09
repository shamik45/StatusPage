package com.shamik.code.statuspage.spring.controller.repository;

import com.shamik.code.statuspage.spring.controller.objects.UserInfo;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by shamik.shah on 11/15/17.
 */
public interface UserInfoRepository extends CrudRepository<UserInfo, Long>
{
    List<UserInfo> findByFirstName(String firstName);

    @Transactional
    Long deleteByFirstName(String firstName);

}
