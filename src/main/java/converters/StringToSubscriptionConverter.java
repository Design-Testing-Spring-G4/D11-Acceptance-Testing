
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.SubscriptionRepository;
import domain.Subscription;

@Component
@Transactional
public class StringToSubscriptionConverter implements Converter<String, Subscription> {

	@Autowired
	SubscriptionRepository	subscriptionRepository;


	@Override
	public Subscription convert(final String s) {
		Subscription result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.subscriptionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
