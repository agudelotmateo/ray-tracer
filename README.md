# Ray Tracer

This is a simple real time ray tracer. It reads object description files that follow this format:

```
Number of Point Lights
List of Point Lights: <x y z redWeight greenWeight blueWeight>
Number of Materials
List of Materials: <ambientWeight diffuseWeight specularWeight colorWeight reflectionWeight refractionWeight specularExponent redWeight greenWeight blueWeight >
Number of Vertices
List of Vertices: <x y z>
Number of Triangles
List of Triangles: <firstVertexIndex, secondVertexIndex, thirdVertexIndex, materialIndex>
```

Things to keep in mind:

- All the weights must be given in percentages (i.e. their values must lie in the [0-100] range)
- Triangle points must be given in CCW order looking from the outside of it. 
- Indexes start at 0. 

"casitaTriangleMesh.txt" is an example of a valid input file. 

This are the controls:

- **Space bar:** open file
- **Numpad 5:** move the scene down (Y)
- **Numpad 8:** move the scene up (Y)
- **Numpad 4:** move the scene left (X)
- **Numpad 6:** move the scene right (X)
- **Numpad 7:** move the scene away (Z)
- **Numpad 9:** move the scene closer (Z)
- **Numpad 1:** make the objects in the scene bigger
- **Numpad 3:** make the objects in the scene smaller
- **W:** translate the objects in the scene up (Y) 
- **s:** translate the objects in the scene down (Y)
- **d:** translate the objects in the scene right (X)
- **a:** translate the objects in the scene left (X)
- **e:** translate the objects in the scene closer (Z)
- **q:** translate the objects in the scene away (Z)
- **r:** rotate the objects in the scene around +X
- **t:** rotate the objects in the scene around -X
- **f:** rotate the objects in the scene around +Y
- **g:** rotate the objects in the scene around -Y
- **v:** rotate the objects in the scene around +Z
- **b:** rotate the objects in the scene around -Z
