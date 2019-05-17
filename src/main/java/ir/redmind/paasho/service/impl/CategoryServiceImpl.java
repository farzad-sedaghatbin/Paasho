package ir.redmind.paasho.service.impl;

import ir.redmind.paasho.domain.Category;
import ir.redmind.paasho.repository.CategoryRepository;
import ir.redmind.paasho.service.CategoryService;
import ir.redmind.paasho.service.dto.CategoryDTO;
import ir.redmind.paasho.service.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Category.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;


    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        CategoryDTO result = categoryMapper.toDto(category);
        return result;
    }

    /**
     * Get all the categories.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        log.debug("Request to get all Categories");
        return categoryRepository.findAllWithEagerRelationships().stream()
            .map(categoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Category with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<CategoryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return categoryRepository.findAllWithEagerRelationships(pageable).map(categoryMapper::toDto);
    }
    

    /**
     * Get one category by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryDTO> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findOneWithEagerRelationships(id)
            .map(categoryMapper::toDto);
    }

    /**
     * Delete the category by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }

    /**
     * Search for the category corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> search(String query) {
        log.debug("Request to search Categories for query {}", query);
        return null;
    }
}
