# BMI Calculator – Jetpack Compose

Primera pràctica d'Android amb **Kotlin** i **Jetpack Compose** (2n DAM – Institut Baix Camp).

És una calculadora d'**IMC (Índex de Massa Corporal)**. L'usuari introdueix el seu nom, tria l'alçada, ajusta el pes i calcula l'IMC. L'app li mostra el valor i la categoria que li correspon.

## Captura de pantalla

<p align="center">
  <img src="screenshots/captura.png" alt="Captura de la interfície de l'app" width="300"/>
</p>

## Funcionalitats

| Component | Què fa |
|---|---|
| `OutlinedTextField` | Camp per escriure el **nom** de l'usuari. |
| `Slider` | Selecciona l'**alçada** entre 100 i 220 cm. El valor s'actualitza en temps real. |
| `FilledTonalButton` `-` / `+` | Baixa o puja el **pes** d'un en un (de 30 a 250 kg). Cada botó es deshabilita quan s'arriba al límit. |
| `Button` "Calcular IMC" | Valida les dades i calcula l'IMC. |
| `Card` de resultat | Mostra l'IMC amb un decimal, la categoria i una breu descripció. Està **oculta** fins que es calcula. |
| `OutlinedButton` "Tornar a començar" | Reinicia tots els valors i torna a amagar el resultat. |

### Validació de dades

- El nom **no pot estar buit**, ha de tenir **com a mínim 2 lletres** i **només pot contenir lletres** (s'accepten espais, guions i apòstrofs).
- Si el nom no és vàlid, el camp es marca en vermell i es mostra el missatge d'error a sota. El resultat no es calcula.
- El pes i l'alçada no poden sortir del rang permès perquè el `Slider` i els botons estan limitats.
- Si l'usuari canvia qualsevol dada després de calcular, el resultat antic s'amaga per no mostrar informació incorrecta.

### Càlcul

```
IMC = pes (kg) / alçada (m)²
```

| IMC | Categoria | Color |
|---|---|---|
| < 18,5 | Infrapès | Blau |
| 18,5 – 24,9 | Pes normal | Verd |
| 25 – 29,9 | Sobrepès | Taronja |
| ≥ 30 | Obesitat | Vermell |

## Conceptes treballats

- **Interfície declarativa amb Compose**: la pantalla es construeix amb funcions `@Composable` (`Column`, `Row`, `Card`, `Text`, `Button`...) en lloc de layouts XML.
- **Estat**: les dades es guarden amb `rememberSaveable { mutableStateOf(...) }`. Quan l'estat canvia, Compose torna a dibuixar la interfície automàticament (i l'estat es manté si es gira el mòbil).
- **Esdeveniments de l'usuari**: `onClick`, `onValueChange` del `TextField` i del `Slider`.
- **Mostrar / ocultar components**: el resultat només es pinta quan `mostrarResultat` és `true`.
- **Habilitar / deshabilitar components**: propietat `enabled` dels botons de pes.
- **Separació de la lògica**: els càlculs i la validació són a `CalculIMC.kt` i es comproven amb tests unitaris.

## Estructura del projecte

```
app/src/main/java/org/insbaixcamp/bmicalculator/
├── MainActivity.kt      # Activity i pantalla BMIScreen (interfície en Compose)
├── CalculIMC.kt         # Càlcul de l'IMC, categories i validació del nom
└── ui/theme/            # Colors, tipografia i tema Material 3

app/src/test/java/org/insbaixcamp/bmicalculator/
└── CalculIMCTest.kt     # Tests unitaris de la lògica
```

## Com executar-lo

1. Clona el repositori i obre'l amb **Android Studio**.
2. Espera que Gradle sincronitzi el projecte.
3. Executa l'app en un emulador o en un mòbil (Android 7.0 / API 24 o superior).
4. Per executar els tests: clic dret a `CalculIMCTest` → **Run**.

## Tecnologies

- Kotlin
- Jetpack Compose + Material 3
- Android Studio
- Gradle (Kotlin DSL)

## Autor

Miquel Larios Linares – 2n DAM, Institut Baix Camp (Reus)
