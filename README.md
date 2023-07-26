Class Node has value,grad,childrNodes,op.
Neuron can be thought of as a vector, given an input the forward function will dot product the vector input and this neuron
Layer is a group of neurons, that take a array of length nin, perform dot product of it with all the neurons, to output an array of the dot products of the neurons