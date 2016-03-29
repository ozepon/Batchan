package validate;

import java.text.MessageFormat;

/**
 * バッチ起動引数の数が1つである事
 * @author Mac
 *
 */
public class Validate_001 implements IValidaterArgsChan, IValidaterAncestor{

	@Override
	public void validate(Object obj) throws Exception {
		String[] args = (String[])obj;
		// 起動引数の数が1つである事
		if (args.length != 1) {
			System.out.println(MessageFormat.format(errorMessage.get(001), "引数の数"));
			return;// TODO exceptionまだ
		}				
	}
}
