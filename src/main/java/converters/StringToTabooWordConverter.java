
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.TabooWordRepository;
import domain.TabooWord;

@Component
@Transactional
public class StringToTabooWordConverter implements Converter<String, TabooWord> {

	@Autowired
	TabooWordRepository	tabooWordRepository;


	@Override
	public TabooWord convert(final String s) {
		TabooWord result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.tabooWordRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
