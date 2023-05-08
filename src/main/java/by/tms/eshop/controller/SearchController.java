package by.tms.eshop.controller;

import by.tms.eshop.service.ShopFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.MappingPath.SEARCH_PATH;
import static by.tms.eshop.utils.Constants.RequestParameters.FILTER;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH_CONDITION;
import static by.tms.eshop.utils.Constants.RequestParameters.SELECT;
import static by.tms.eshop.utils.ControllerUtils.removeUnsavedAttribute;
import static by.tms.eshop.utils.ControllerUtils.setFilterAttribute;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final ShopFacade shopFacade;

    @GetMapping("/search")
//    public ModelAndView hasFilterPage(HttpServletRequest request,
    public ModelAndView hasFilterPage(HttpSession session,
                                      @RequestParam(required = false) String result,
                                      @RequestParam(required = false) String filter) {

        //type парам табл

        removeUnsavedAttribute(session, result);
        session.removeAttribute(FILTER);
//        request.getServletContext().removeAttribute(FILTER);
        setFilterAttribute(session, filter);
//        setFilterAttribute(request, filter);
        return new ModelAndView(SEARCH_PATH);
    }

    @PostMapping("/search-param")
//    public ModelAndView showSearchPageByParam(HttpServletRequest request,
    public ModelAndView showSearchPageByParam(HttpSession session,
                                              @RequestParam(name = SEARCH_CONDITION) String searchCondition) {
//        request.getServletContext().removeAttribute(FILTER);
        session.removeAttribute(FILTER);
        shopFacade.returnProductsBySearchCondition(session, searchCondition);
        return new ModelAndView(REDIRECT_TO_SEARCH_RESULT_SAVE);
    }

    @PostMapping("/search-filter")
    public ModelAndView showSearchPageByFilter(HttpServletRequest request,
                                               @RequestParam(required = false, name = SELECT) String type) {
        return shopFacade.getSearchFilterResultPagePath(request, type);
    }
}