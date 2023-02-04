package kz.duman.bulletin_board.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kz.duman.bulletin_board.payload.NewBoardRequest;
import kz.duman.bulletin_board.security.jwt.SecurityUser;
import kz.duman.bulletin_board.service.BulletinBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Bulletin board API", description = "api for ads")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/ads")
public class BulletinBoardController {

    private final BulletinBoardService bulletinBoardService;

    @ApiOperation("Create an ad and post it on the bulletin board")
    @PostMapping
    public ResponseEntity<String> addNewBoard(
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestBody NewBoardRequest request
    ) {
         bulletinBoardService.addNewBoard(request, securityUser.getId());
         return ResponseEntity.ok("Ads successfully created");
    }
}
