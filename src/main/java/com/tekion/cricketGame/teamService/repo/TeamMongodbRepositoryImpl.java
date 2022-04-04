package com.tekion.cricketGame.teamService.repo;

import com.tekion.cricketGame.config.DatabaseConfig.ClassMapper;
import com.tekion.cricketGame.config.DatabaseConfig.ClassMapperMetaInfo;
import com.tekion.cricketGame.config.DatabaseConfig.MongoRepo;
import com.tekion.cricketGame.counterServiceForMongo.service.CounterService;
import com.tekion.cricketGame.teamService.bean.TeamBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@ClassMapperMetaInfo(getClassName = ClassMapper.TEAM_REPO)
public class TeamMongodbRepositoryImpl implements TeamRepository , MongoRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CounterService counterService;

    @Override
    public void createTeam(String teamName) {
        TeamBean team = new TeamBean();
        team.setTeamId(counterService.getNextSequence("team"));
        team.setTeamName(teamName);
        team.setCreatedTime(System.currentTimeMillis());
        team.setModifiedTime(System.currentTimeMillis());
        team.setIsDeleted(false);
        mongoTemplate.save(team , "team");
    }

    @Override
    public Boolean ifCheckTeamExists(String teamName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("teamName").is(teamName));
        long count = mongoTemplate.count(query , "team");
        return count > 0;
    }

    @Override
    public Boolean checkIfTeamIdExists(int teamId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("teamId").is(teamId));
        long count = mongoTemplate.count(query , "team");
        return count > 0;
    }

    @Override
    public int getIdByTeamName(String teamName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("teamName").is(teamName));
        TeamBean team =  mongoTemplate.findOne(query , TeamBean.class , "team");
        return team.getTeamId();
    }

    @Override
    public TeamBean getTeamDetails(int teamId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("teamId").is(teamId));
        TeamBean team = mongoTemplate.findOne(query , TeamBean.class , "team");
        return team;
    }
}
