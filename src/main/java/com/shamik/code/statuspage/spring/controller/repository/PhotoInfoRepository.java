package com.shamik.code.statuspage.spring.controller.repository;

import com.shamik.code.statuspage.spring.controller.objects.PhotoInfo;
import com.shamik.code.statuspage.spring.controller.objects.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by shamik.shah on 11/17/17.
 */
public interface PhotoInfoRepository extends CrudRepository<PhotoInfo, Long>
{
    List<PhotoInfo> findByName(String name);
}