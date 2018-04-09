package com.society.controllers;

import com.society.service.ApplicationService;
import com.society.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/")
public class ApiController {
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getCountries() {
        return new ResponseEntity(applicationService.getCountryList("all"), HttpStatus.OK);
    }

    @RequestMapping(value = "/countries/{countryVal}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getCountryById(@PathVariable String countryVal) {
        if (Integer.parseInt(countryVal) < 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(applicationService.getCountryList(countryVal), HttpStatus.OK);
    }

    @RequestMapping(value = "/countries/{countryVal}/regions", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getRegions(@PathVariable String countryVal) {
        if (Integer.parseInt(countryVal) < 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(applicationService.getRegionList(countryVal, "all"), HttpStatus.OK);
    }

    @RequestMapping(value = "/countries/{countryVal}/regions/{stateVal}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getRegionById(@PathVariable String countryVal, @PathVariable String stateVal) {
        if (Integer.parseInt(countryVal) < 0 || Integer.parseInt(stateVal) < 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(applicationService.getRegionList(countryVal, stateVal), HttpStatus.OK);
    }

    @RequestMapping(value = "/countries/{countryVal}/regions/{stateVal}/cities", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getCities(@PathVariable String countryVal, @PathVariable String stateVal) {
        if (Integer.parseInt(stateVal) < 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(applicationService.getCityList(stateVal, "all"), HttpStatus.OK);
    }

    @RequestMapping(value = "/countries/{countryVal}/regions/{stateVal}/cities/{cityVal}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getCityById(@PathVariable String countryVal, @PathVariable String stateVal, @PathVariable String cityVal) {
        if (Integer.parseInt(cityVal) < 0 || Integer.parseInt(stateVal) < 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(applicationService.getCityList(stateVal, cityVal), HttpStatus.OK);
    }

    @RequestMapping(value = "/titles", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getAllTitles() {
        return new ResponseEntity(applicationService.getTitleList("all"), HttpStatus.OK);
    }

    @RequestMapping(value = "/titles/{titleVal}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getTitles(@PathVariable String titleVal) {
        if (Integer.parseInt(titleVal) < 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(applicationService.getTitleList(titleVal), HttpStatus.OK);
    }

    @RequestMapping(value = "/genders", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getGenders() {
        return new ResponseEntity(applicationService.getGenderList("all"), HttpStatus.OK);
    }

    @RequestMapping(value = "/genders/{genderVal}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getGenderById(@PathVariable String genderVal) {
        if (Integer.parseInt(genderVal) < 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(applicationService.getGenderList(genderVal), HttpStatus.OK);
    }

    @RequestMapping(value = "/trans-modes", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getTransactionModes() {
        return new ResponseEntity(applicationService.getTransactionModeList("all"), HttpStatus.OK);
    }

    @RequestMapping(value = "/trans-modes/{transModeVal}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getTransactionModesById(@PathVariable String transModeVal) {
        if (Integer.parseInt(transModeVal) < 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(applicationService.getTransactionModeList(transModeVal), HttpStatus.OK);
    }

    @RequestMapping(value = "/trans-selects/selection/{selectionId}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getTransactionSelectsByType(@PathVariable String selectionId) {
        if (Integer.parseInt(selectionId) < 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(applicationService.getTransactionSelectList("all", Integer.parseInt(selectionId)), HttpStatus.OK);
    }

    @RequestMapping(value = "/trans-types", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getTransationTypes() {
        return new ResponseEntity(applicationService.getTransactionTypeList("all"), HttpStatus.OK);
    }

    @RequestMapping(value = "/trans-types/{transTypeVal}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getTransationTypesById(@PathVariable String transTypeVal) {
        if (Integer.parseInt(transTypeVal) < 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(applicationService.getTransactionTypeList(transTypeVal), HttpStatus.OK);
    }
}

