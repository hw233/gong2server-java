package com.gamejelly.gong2.dbs.proxy;

import com.gamejelly.gong2.dbs.annotation.AutoDbsCommandProxy;
import com.gamejelly.gong2.dbs.service.BuildingService;
import com.gamejelly.gong2.utils.GongDbConstants;
import com.hadoit.game.engine.guardian.dbs.base.CommandRpc;
import com.hadoit.game.engine.guardian.dbs.base.RawCommandResult;
import com.hadoit.game.engine.guardian.dbs.proxy.DbsCommandProxy;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AutoDbsCommandProxy
@Component
public class BuildingCommandProxy implements DbsCommandProxy {
    @Autowired
    private BuildingService buildingService;

    @CommandRpc(fullAlias = GongDbConstants.CMD_FIND_BUILDING_LIST_BY_PARENT_ID, unpack = true)
    public RawCommandResult findBuildingListByParentId(int parentId) {
        GuardianLogger.info("findBuildingListByParentId",parentId);
        return new RawCommandResult(buildingService.findBuildingListByParentId(parentId));
    }

}
