package probe.airways2.web.controllers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ControllerBase<Entity, EntityPresentation> {

  protected abstract CrudRepository<Entity, String> getMainRepository();

  protected abstract EntityPresentation createEntityPresentation();

  protected abstract String getListView();

  protected abstract String getEditView();

  protected abstract String getDeleteView();

  protected abstract String getId(EntityPresentation entityPresentation);

  protected abstract Entity toEntity(EntityPresentation entityPresentation);

  protected abstract EntityPresentation toEntityPresentation(Entity entity);

  @GetMapping
  public ModelAndView list() {
    return new ModelAndView(getListView(), "list", getMainRepository().findAll());
  }

  @GetMapping("/new")
  public ModelAndView create() {
    return openEntityPresentation(createEntityPresentation(), getEditView());
  }

  @GetMapping("/edit/{id}")
  public ModelAndView edit(@PathVariable("id") String id) {
    return openEntityInViewIfExists(id, getEditView());
  }

  @GetMapping("/delete/{id}")
  public ModelAndView delete(@PathVariable("id") String id) {
    return openEntityInViewIfExists(id, getDeleteView());
  }

  @PostMapping("/save")
  public ModelAndView save(
    @ModelAttribute("object") @Valid EntityPresentation entityPresentation,
    BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return openEntityPresentation(entityPresentation, bindingResult, getEditView());
    } else {
      final Entity entity = toEntity(entityPresentation);
      getMainRepository().save(entity);
      return list();
    }
  }

  @PostMapping("/confirmDelete")
  public ModelAndView confirmDelete(@ModelAttribute("object") EntityPresentation entityPresentation) {
    getMainRepository().deleteById(getId(entityPresentation));
    return list();
  }

  // ---
  private ModelAndView openEntityInViewIfExists(String id, String view) {
    return getMainRepository().findById(id).stream()
      .map(this::toEntityPresentation)
      .map(entityPresentation -> openEntityPresentation(entityPresentation, view))
      .findFirst()
      .orElseGet(() -> {
        final ModelAndView result = list();
        result.addObject("errorRecordAbsents",
          "Record with id=\"" + id + "\" was externally deleted.");
        return result;
      });
  }

  private ModelAndView openEntityPresentation(
    EntityPresentation entityPresentation,
    BindingResult bindingResult,
    String view) {

    final ModelAndView result = openEntityPresentation(entityPresentation, view);
    dispatchErrors(bindingResult, result);
    return result;
  }

  private void dispatchErrors(BindingResult bindingResult, ModelAndView modelAndView) {
    final List<String> errors = bindingResult.getAllErrors().stream()
      .map( error -> error.getDefaultMessage())
      .collect(Collectors.toList());
    modelAndView.addObject("errors", errors);
  }

  private ModelAndView openEntityPresentation(
    EntityPresentation entityPresentation,
    String view) {
    final ModelAndView result = new ModelAndView(view, "object", entityPresentation);
    extendModel(result);
    return result;
  }

  protected void extendModel(ModelAndView modelAndView) {
    // by default do nothing. May be overridden
  }

}
