
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TabooWordRepository;
import domain.TabooWord;

@Service
@Transactional
public class TabooWordService {

	//Managed repository ---------------------------------

	@Autowired
	private TabooWordRepository	tabooWordRepository;


	//Simple CRUD Methods --------------------------------

	public TabooWord create() {
		final TabooWord tabooWord = new TabooWord();

		return tabooWord;
	}

	public Collection<TabooWord> findAll() {
		return this.tabooWordRepository.findAll();
	}

	public TabooWord findOne(final int id) {
		Assert.notNull(id);

		return this.tabooWordRepository.findOne(id);
	}

	public TabooWord save(final TabooWord tabooWord) {
		Assert.notNull(tabooWord);

		final TabooWord saved = this.tabooWordRepository.save(tabooWord);

		return saved;
	}

	public void delete(final TabooWord tabooWord) {
		Assert.notNull(tabooWord);

		this.tabooWordRepository.delete(tabooWord);
	}
}
