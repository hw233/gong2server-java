package com.gamejelly.gong2.gas.service.shared;

import com.gamejelly.gong2.gas.entity.SharedEntity;
import com.hadoit.game.engine.guardian.gas.GasManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("sharedDataService")
public class SharedDataService {

    @Autowired
    @Qualifier("gasManager")
    private GasManager gasManager;

    private SharedEntity sharedEntity;

    public SharedEntity getSharedEntity() {
        return sharedEntity;
    }

    public void setSharedEntity(SharedEntity sharedEntity) {
        this.sharedEntity = sharedEntity;
    }

}
