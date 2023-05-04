package by.tms.eshop.controller;

import by.tms.eshop.service.Facade;
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

    private final Facade facade;

    @GetMapping("/search")
    public ModelAndView hasFilterPage(HttpServletRequest request,
                                      HttpSession session,
                                      @RequestParam(required = false) String result,
                                      @RequestParam(required = false) String filter) {
        removeUnsavedAttribute(session, result);
        request.getServletContext().removeAttribute(FILTER);
        setFilterAttribute(request, filter);
        return new ModelAndView(SEARCH_PATH);
    }

    @PostMapping("/search-param")
    public ModelAndView showSearchPageByParam(HttpServletRequest request,
                                              HttpSession session,
                                              @RequestParam(name = SEARCH_CONDITION) String searchCondition) {
        request.getServletContext().removeAttribute(FILTER);
        facade.returnProductsBySearchCondition(session, searchCondition);
        return new ModelAndView(REDIRECT_TO_SEARCH_RESULT_SAVE);
    }

    @PostMapping("/search-filter")
    public ModelAndView showSearchPageByFilter(HttpServletRequest request,
                                               @RequestParam(required = false, name = SELECT) String type) {
        return new ModelAndView(facade.getSearchFilterResultPagePath(request, type));
    }
}