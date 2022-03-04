package com.tekion.cricketGame.cricketSeriesService.repo;

import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CricketSeriesRepo extends CrudRepository<CricketSeriesBean, Integer> {


}
