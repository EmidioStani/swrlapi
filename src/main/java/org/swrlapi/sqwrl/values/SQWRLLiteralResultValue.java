package org.swrlapi.sqwrl.values;

import org.swrlapi.literal.Literal;

/**
 * Represents a literal result value provided by a {@link org.swrlapi.sqwrl.SQWRLResult}.
 * 
 * @see org.swrlapi.sqwrl.SQWRLResult
 * @see org.swrlapi.literal.Literal
 */
public interface SQWRLLiteralResultValue extends SQWRLResultValue, Comparable<SQWRLLiteralResultValue>, Literal
{
  /**
   * @return The prefixed name of the literal's datatype
   */
  String getDatatypePrefixedName();
}
