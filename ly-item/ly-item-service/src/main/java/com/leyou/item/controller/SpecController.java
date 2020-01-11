package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    private SpecService specService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroups(@PathVariable("cid") Long id ){
        List<SpecGroup> groupList= specService.querySpecGroups(id);
        if(null!=groupList&&groupList.size()>0){
            return  ResponseEntity.ok(groupList);

        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();


    }

    @GetMapping("params")
    public  ResponseEntity<List<SpecParam>> querySpecParam(@RequestParam(value = "gid",required = false) Long gid,
                                                           @RequestParam(value = "cid",required = false) Long cid,
                                                           @RequestParam(value = "searching",required = false) Boolean searching,
                                                           @RequestParam(value = "generic",required = false) Boolean generic){
        List<SpecParam> specParamList=specService.querySpecParam(gid,cid,searching,generic);

        if(null!=specParamList&&specParamList.size()>0){
            return  ResponseEntity.ok(specParamList);

        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();



    }



}
