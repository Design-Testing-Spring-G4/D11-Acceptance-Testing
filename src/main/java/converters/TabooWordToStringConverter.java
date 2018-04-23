
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.TabooWord;

@Component
@Transactional
public class TabooWordToStringConverter implements Converter<TabooWord, String> {

	@Override
	public String convert(final TabooWord t) {
		String result;

		if (t == null)
			result = null;
		else
			result = String.valueOf(t.getId());

		return result;
	}
}
