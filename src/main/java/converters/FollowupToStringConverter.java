
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Followup;

@Component
@Transactional
public class FollowupToStringConverter implements Converter<Followup, String> {

	@Override
	public String convert(final Followup f) {
		String result;

		if (f == null)
			result = null;
		else
			result = String.valueOf(f.getId());

		return result;
	}
}
