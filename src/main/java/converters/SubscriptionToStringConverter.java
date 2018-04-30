
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Subscription;

@Component
@Transactional
public class SubscriptionToStringConverter implements Converter<Subscription, String> {

	@Override
	public String convert(final Subscription s) {
		String result;

		if (s == null)
			result = null;
		else
			result = String.valueOf(s.getId());

		return result;
	}
}
