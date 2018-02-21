package com.shamik.code.statuspage.spring.controller.repository;

import com.shamik.code.statuspage.spring.controller.objects.CalendarInfo;
import com.shamik.code.statuspage.spring.controller.objects.GoogleAuthInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by shamik.shah on 2/19/18.
 */
public interface GoogleAuthInfoRepository extends CrudRepository<GoogleAuthInfo, Long>{

    List<GoogleAuthInfo> findByParameter(String parameter);
}
