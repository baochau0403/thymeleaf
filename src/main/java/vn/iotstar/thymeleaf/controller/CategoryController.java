package vn.iotstar.thymeleaf.controller;

import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vn.iotstar.thymeleaf.models.CategoryModel;
import vn.iotstar.thymeleaf.service.CategoryService;


@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("")
	public String all(Model model) {
		List<Category> list = categoryService.findAll();
		model.addAttribute("list", list);
		return "admin/category/list";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		CategoryModel category = new CategoryModel();
		category.setIsEdit(false);
		model.addAttribute("category", category);
		return "admin/category/add";
	}
	
	@PostMapping("/save")
	public ModelAndView saveorupdate(ModelMap model, 
			@Valid @ModelAttribute("category") CategoryModel cateModel,BindingResult result)
	{
		if (result.hasErrors()) {
			return new ModelAndView("admin/category/add");
		}
		Category entity = new Category();

		BeanUtils.copyProperties(cateModel, entity);

		categoryService.save(entity);

		String message = "";
		if (cateModel.getIsEdit() == true) {
			message = "Them thanh cong";
		}
		else
		{
			message="Them khong thanh cong";
		}

		model.addAttribute("message", message);
		return new ModelAndView("forward:/admin/categories", model);
		
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(ModelMap model, @PathVariable("id") Long categoryId) {
		Optional<Category> optCate = categoryService.findById(categoryId);
		CategoryModel cateModel = new CategoryModel();
		if (optCate.isPresent()) {
			Category entity = new Category();
			
			BeanUtils.copyProperties(entity, cateModel);
			cateModel.setIsEdit(true);
			model.addAttribute("cate", cateModel);
			return new ModelAndView("admin/category/add", model);
		}
		model.addAttribute("message", "category is not existed");
		return new ModelAndView("forward:/admin/categories", model);
	}
	
	@GetMapping("delete/{id}")
	public ModelAndView delete(ModelMap model, @PathVariable("id") Long categoryId) {
		categoryService.deleteById(categoryId);
		model.addAttribute("message","Deleted!");
		return new ModelAndView("forward:/admin/categories", model);
	}
	
	
}