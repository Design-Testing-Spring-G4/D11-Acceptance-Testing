
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FollowupRepository;
import domain.Followup;

@Component
@Transactional
public class StringToFollowupConverter implements Converter<String, Followup> {

	@Autowired
	FollowupRepository	followupRepository;


	@Override
	public Followup convert(final String s) {
		Followup result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.followupRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
