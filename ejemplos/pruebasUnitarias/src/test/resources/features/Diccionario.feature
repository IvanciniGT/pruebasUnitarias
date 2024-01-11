#language:es
Característica: Componente diccionario

  Antecedentes:
    Dado     que tengo un diccionario de idioma "ES"

  Escenario: Preguntar por una palabra que existe en el diccionario
    Dado     que la palabra "manzana" existe en el diccionario
    Cuando   pregunto por esa palabra al diccionario
    Entonces el diccionario me responde que "si" existe

  Escenario: Preguntar por una palabra que no existe en el diccionario
    Dado     que la palabra "manana" no existe en el diccionario
    Cuando   pregunto por esa palabra al diccionario
    Entonces el diccionario me responde que "no" existe

  Esquema del escenario: Obtener los significados de una palabra existente en el diccionario
    Dado     que la palabra "<palabra>" existe en el diccionario
    Cuando   solicito los significados de esa palabra al diccionario
    Entonces el diccionario me devuelve una lista con los significados de esa palabra, en total <numeroSignificados>
    Y        el significado número <posicion> que aparece en esa lista es "<significado>"

    Ejemplos:
      | palabra | numeroSignificados | significado          | posicion |
      | manzana | 1                  | Fruta del manzano    | 1        |
      | perro   | 2                  | Animal de compañía   | 1        |
      | perro   | 2                  | Persona muy perezosa | 2        |

