package validate;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 	バッチ起動引数が"BATCH"で始まる事
 * @author Mac
 *
 */
public class Validate_002 implements IValidaterArgsChan, IValidaterAncestor{

	@Override
	public void validate(Object obj) throws Exception {
		String[] args = (String[]) obj;

		Pattern p = Pattern.compile("^BATCH");
		Matcher m = p.matcher(args[0]);
		if (!m.find()) {
			System.out.println(MessageFormat.format(errorMessage.get(001), "起動するバッチ名"));
			return;// TODO exceptionまだ
		}
	}
}
