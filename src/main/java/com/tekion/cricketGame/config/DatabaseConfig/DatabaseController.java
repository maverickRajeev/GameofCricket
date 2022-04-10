package com.tekion.cricketGame.config.DatabaseConfig;

import com.tekion.cricketGame.CricketGameApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class DatabaseController {

    @GetMapping(value = "/showDb")
    public @ResponseBody ResponseEntity<String> showDbType(){
        DbType databaseType = DataProvider.currentEnvironmentType;
        return ResponseEntity.ok().body("Current Database Type : " + databaseType);
    }

    @GetMapping(value = "/changeDb/{dbType}")
    public @ResponseBody ResponseEntity<String> changeDbType(@PathVariable("dbType") String dbType){
        DbType databaseType = DbType.valueOf(dbType.toUpperCase());
        if(DataProvider.currentEnvironmentType != databaseType) {
            DataProvider.runtimeUpdateDbType(databaseType);
            CricketGameApplication.restart();
        }
        return ResponseEntity.ok().body("Database type changed successfully to " + dbType.toUpperCase());
    }

}
