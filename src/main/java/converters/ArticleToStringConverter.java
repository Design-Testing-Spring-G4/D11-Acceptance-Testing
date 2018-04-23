
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Article;

@Component
@Transactional
public class ArticleToStringConverter implements Converter<Article, String> {

	@Override
	public String convert(final Article a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
