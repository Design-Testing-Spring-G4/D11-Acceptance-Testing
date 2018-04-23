
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.NewspaperRepository;
import domain.Newspaper;

@Component
@Transactional
public class StringToNewspaperConverter implements Converter<String, Newspaper> {

	@Autowired
	NewspaperRepository	newspaperRepository;


	@Override
	public Newspaper convert(final String s) {
		Newspaper result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.newspaperRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
