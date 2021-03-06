package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyExpressionBuiltInArgument;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.literal.Literal;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassExpressionResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyExpressionResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLDatatypeResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLNamedIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyExpressionResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultSQWRLResultValueFactory implements SQWRLResultValueFactory
{
  @NonNull private final IRIResolver iriResolver;
  @NonNull private final OWLLiteralFactory owlLiteralFactory;

  public DefaultSQWRLResultValueFactory(@NonNull IRIResolver iriResolver)
  {
    this.iriResolver = iriResolver;
    this.owlLiteralFactory = SWRLAPIInternalFactory.createOWLLiteralFactory();
  }

  @NonNull @Override public SQWRLClassResultValue getClassValue(@NonNull SWRLClassBuiltInArgument classArgument)
  {
    return getClassValue(classArgument.getIRI());
  }

  @NonNull @Override public SQWRLClassResultValue getClassValue(@NonNull IRI classIRI)
  {
    String prefixedName = iri2PrefixedName(classIRI);
    String shortForm = iri2ShortForm(classIRI);

    return new DefaultSQWRLClassResultValue(classIRI, prefixedName, shortForm);
  }

  @NonNull @Override public SQWRLClassExpressionResultValue getClassExpressionValue(
    @NonNull SWRLClassExpressionBuiltInArgument classExpressionArgument)
  {
    OWLClassExpression ce = classExpressionArgument.getOWLClassExpression();
    String rendering = getIRIResolver().render(ce);

    return new DefaultSQWRLClassExpressionResultValue(rendering);
  }

  @NonNull @Override public SQWRLNamedIndividualResultValue getNamedIndividualValue(
    @NonNull SWRLNamedIndividualBuiltInArgument individualArgument)
  {
    String prefixedName = iri2PrefixedName(individualArgument.getIRI());
    String shortForm = iri2ShortForm(individualArgument.getIRI());

    return new DefaultSQWRLNamedIndividualResultValue(individualArgument.getIRI(), prefixedName, shortForm);
  }

  @NonNull @Override public SQWRLNamedIndividualResultValue getNamedIndividualValue(@NonNull IRI individualIRI)
  {
    String prefixedName = iri2PrefixedName(individualIRI);
    String shortForm = iri2ShortForm(individualIRI);

    return new DefaultSQWRLNamedIndividualResultValue(individualIRI, prefixedName, shortForm);
  }

  @NonNull @Override public SQWRLObjectPropertyResultValue getObjectPropertyValue(
    @NonNull SWRLObjectPropertyBuiltInArgument objectPropertyArgument)
  {
    return getObjectPropertyValue(objectPropertyArgument.getIRI());
  }

  @NonNull @Override public SQWRLObjectPropertyResultValue getObjectPropertyValue(@NonNull IRI propertyIRI)
  {
    String prefixedName = iri2PrefixedName(propertyIRI);
    String shortForm = iri2ShortForm(propertyIRI);

    return new DefaultSQWRLObjectPropertyResultValue(propertyIRI, prefixedName, shortForm);
  }

  @NonNull @Override public SQWRLObjectPropertyExpressionResultValue getObjectPropertyExpressionValue(
    SWRLObjectPropertyExpressionBuiltInArgument objectPropertyExpressionArgument)
  {
    OWLObjectPropertyExpression pe = objectPropertyExpressionArgument.getOWLObjectPropertyExpression();
    String rendering = getIRIResolver().render(pe);

    return new DefaultSQWRLObjectPropertyExpressionResultValue(rendering);
  }

  @NonNull @Override public SQWRLDataPropertyResultValue getDataPropertyValue(
    @NonNull SWRLDataPropertyBuiltInArgument dataPropertyArgument)
  {
    String prefixedName = iri2PrefixedName(dataPropertyArgument.getIRI());
    String shortForm = iri2ShortForm(dataPropertyArgument.getIRI());

    return new DefaultSQWRLDataPropertyResultValue(dataPropertyArgument.getIRI(), prefixedName, shortForm);
  }

  @NonNull @Override public SQWRLDataPropertyResultValue getDataPropertyValue(@NonNull IRI propertyIRI)
  {
    String prefixedName = iri2PrefixedName(propertyIRI);
    String shortForm = iri2ShortForm(propertyIRI);

    return new DefaultSQWRLDataPropertyResultValue(propertyIRI, prefixedName, shortForm);
  }

  @NonNull @Override public SQWRLDataPropertyExpressionResultValue getDataPropertyExpressionValue(
    SWRLDataPropertyExpressionBuiltInArgument dataPropertyExpressionArgument)
  {
    OWLDataPropertyExpression pe = dataPropertyExpressionArgument.getOWLDataPropertyExpression();
    String rendering = getIRIResolver().render(pe);

    return new DefaultSQWRLDataPropertyExpressionResultValue(rendering);
  }

  @NonNull @Override public SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(
    @NonNull SWRLAnnotationPropertyBuiltInArgument annotationPropertyArgument)
  {
    String prefixedName = iri2PrefixedName(annotationPropertyArgument.getIRI());
    String shortForm = iri2ShortForm(annotationPropertyArgument.getIRI());

    return new DefaultSQWRLAnnotationPropertyResultValue(annotationPropertyArgument.getIRI(), prefixedName, shortForm);
  }

  @NonNull @Override public SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(@NonNull IRI propertyIRI)
  {
    String prefixedName = iri2PrefixedName(propertyIRI);
    String shortForm = iri2ShortForm(propertyIRI);

    return new DefaultSQWRLAnnotationPropertyResultValue(propertyIRI, prefixedName, shortForm);
  }

  @NonNull @Override public SQWRLDatatypeResultValue getDatatypeValue(SWRLDatatypeBuiltInArgument datatypeArgument)
  {
    IRI propertyIRI = datatypeArgument.getOWLDatatype().getIRI();
    String prefixedName = iri2PrefixedName(propertyIRI);
    String shortForm = iri2ShortForm(propertyIRI);

    return new DefaultSQWRLDatatypeResultValue(propertyIRI, prefixedName, shortForm);
  }

  @NonNull @Override public SQWRLDatatypeResultValue getDatatypeValue(IRI propertyIRI)
  {
    String prefixedName = iri2PrefixedName(propertyIRI);
    String shortForm = iri2ShortForm(propertyIRI);

    return new DefaultSQWRLDatatypeResultValue(propertyIRI, prefixedName, shortForm);
  }

  @NonNull @Override public SQWRLLiteralResultValue getLiteralValue(byte b)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(b),
      XSDVocabulary.BYTE.getPrefixedName());
  }

  @NonNull @Override public SQWRLLiteralResultValue getLiteralValue(short s)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(s),
      XSDVocabulary.SHORT.getPrefixedName());
  }

  @NonNull @Override public SQWRLLiteralResultValue getLiteralValue(int i)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(i),
      XSDVocabulary.INT.getPrefixedName());
  }

  @NonNull @Override public SQWRLLiteralResultValue getLiteralValue(long l)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(l),
      XSDVocabulary.LONG.getPrefixedName());
  }

  @NonNull @Override public SQWRLLiteralResultValue getLiteralValue(float f)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(f),
      XSDVocabulary.FLOAT.getPrefixedName());
  }

  @NonNull @Override public SQWRLLiteralResultValue getLiteralValue(double d)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(d),
      XSDVocabulary.DOUBLE.getPrefixedName());
  }

  @NonNull @Override public SQWRLLiteralResultValue getLiteralValue(@NonNull String s)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(s),
      XSDVocabulary.STRING.getPrefixedName());
  }

  @NonNull @Override public SQWRLLiteralResultValue getLiteralValue(boolean b)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(b),
      XSDVocabulary.BOOLEAN.getPrefixedName());
  }

  @NonNull @Override public SQWRLLiteralResultValue getLiteralValue(@NonNull URI uri)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(uri),
      XSDVocabulary.ANY_URI.getPrefixedName());
  }

  @NonNull @Override public SQWRLLiteralResultValue getLiteralValue(@NonNull XSDTime time)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(time),
      XSDVocabulary.TIME.getPrefixedName());
  }

  @NonNull @Override public SQWRLLiteralResultValue getLiteralValue(@NonNull XSDDate date)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(date),
      XSDVocabulary.DATE.getPrefixedName());
  }

  @NonNull @Override public SQWRLLiteralResultValue getLiteralValue(@NonNull XSDDateTime dateTime)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(dateTime),
      XSDVocabulary.DATE_TIME.getPrefixedName());
  }

  @NonNull @Override public SQWRLLiteralResultValue getLiteralValue(@NonNull XSDDuration duration)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(duration),
      XSDVocabulary.DURATION.getPrefixedName());
  }

  @NonNull @Override public SQWRLLiteralResultValue getLiteralValue(@NonNull OWLLiteral literal)
  {
    IRI datatypeIRI = literal.getDatatype().getIRI();

    return new DefaultSQWRLLiteralResultValue(literal, iri2PrefixedName(datatypeIRI));
  }

  @NonNull @Override public SQWRLLiteralResultValue createLeastNarrowNumericLiteralValue(double value,
    @NonNull List<@NonNull SQWRLLiteralResultValue> inputResultValues)
  {
    List<@NonNull OWLLiteral> numericLiterals = inputResultValues.stream().filter(Literal::isNumeric)
      .map(Literal::getOWLLiteral).collect(Collectors.toList());

    OWLLiteral literal = getOWLLiteralFactory().createLeastNarrowNumericOWLLiteral(value, numericLiterals);

    return getLiteralValue(literal);
  }

  @NonNull private String iri2PrefixedName(IRI iri)
  {
    Optional<@NonNull String> prefixedName = this.iriResolver.iri2PrefixedName(iri);

    if (prefixedName.isPresent())
      return prefixedName.get();
    else
      throw new IllegalArgumentException("could not get prefixed name for IRI " + iri);
  }

  @NonNull private String iri2ShortForm(IRI iri)
  {
    Optional<@NonNull String> shortshortForm = this.iriResolver.iri2ShortForm(iri);

    if (shortshortForm.isPresent())
      return shortshortForm.get();
    else
      throw new IllegalArgumentException("could not get short form for IRI " + iri);
  }

  @NonNull private OWLLiteralFactory getOWLLiteralFactory()
  {
    return this.owlLiteralFactory;
  }

  @NonNull private IRIResolver getIRIResolver()
  {
    return this.iriResolver;
  }
}
