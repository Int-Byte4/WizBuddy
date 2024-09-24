package com.intbyte.wizbuddy.board.command.infrastructure.event;

import com.intbyte.wizbuddy.board.command.infrastructure.service.InfraSubsBoardService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SubsBoardEventListener {

    private final InfraSubsBoardService infraSubsBoardService;

    public SubsBoardEventListener(InfraSubsBoardService infraSubsBoardService) {
        this.infraSubsBoardService = infraSubsBoardService;
    }

    @EventListener
    public void handleSubsBoardDeleted(SubsBoardDeletedEvent event) {
        infraSubsBoardService.SubsBoardByCommentFlag(event.getSubsCode());
    }
}
