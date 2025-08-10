# Architecture modulaire – Formation Management Systeme

## Objectifs
- **Évolutif** : ajouter des fonctionnalités sans toucher au noyau (modules, quiz, certificats…).
- **Faiblement couplé** : le cœur ne connaît que des **interfaces** et des **points d’extension**.
- **Remplaçable** : un plugin peut être activé/désactivé, voire remplacé, sans réécrire l’app.

---

## Vue d’ensemble

```
formation-management-systeme (Reactor Maven)
├─ core/                ← noyau applicatif exécuté (Spring Boot)
│  ├─ com.formation.core.Application
│  ├─ engine/           ← PluginManager (gestion cycle de vie des plugins)
│  ├─ plugin/           ← Contrats plugins (Plugin, PluginStatus)
│  │  └─ impl/          ← Plugins “in-process” (Quiz, Certificate, ModuleManagement)
│  ├─ model/            ← Domaine (Formation, FormationStatus)
│  ├─ repository/       ← Spring Data (FormationRepository)
│  ├─ service/          ← Services métier (FormationService)
│  ├─ api/              ← REST (ApiFormationController, ApiPluginController, HomeController)
│  └─ resources/
│     ├─ static/        ← assets statiques (index.html, css, js)
│     └─ application.properties
│
├─ api/                 ← (optionnel) exposition API dédiée (si découplage souhaité)
├─ pluginloader/        ← (optionnel) chargement dynamique .jar (classpath externe)
├─ interfacecli/        ← (optionnel) interface ligne de commande
└─ plugins/             ← (optionnel) plugins packagés séparément
   ├─ modulemanagement/
   ├─ quiz/
   └─ certificate/
```

- **core** est suffisant pour démarrer (plugins *in-process* via `@Component`).
- Les modules `pluginloader`, `plugins/*` permettent d’aller vers du **chargement dynamique** (JAR externes).

---

## Noyau (core)

### Démarrage
- `Application` est l’entrée Spring Boot.
- `application.properties` configure :
  - H2 en mémoire (`jdbc:h2:mem:testdb`), console sur `/formation/h2-console`.
  - `server.servlet.context-path=/formation` → toutes les URLs commencent par `/formation`.

### Domaine & persistance
- `model.Formation` : entité JPA (id `String`, `title`, `description`, `status`, `participants`).
- `model.FormationStatus` : `DRAFT`, `COMPLETED`, etc.
- `repository.FormationRepository extends JpaRepository<Formation, String>`.
- `service.FormationService` : règles simples (create, get, delete, complete).

### API REST (extraits)
- **Formations** (`ApiFormationController`, **base: `/formation/api/formations`**)
  - `GET /` → liste
  - `POST /` → créer
  - `GET /{id}` → détail
  - `DELETE /{id}` → supprimer
  - `POST /{id}/complete` → marquer terminée
- **Plugins** (`ApiPluginController`, **base: `/formation/api/plugins`**)
  - `GET /` → liste des plugins (name, version, icon, status)
  - `POST /{name}/start` / `POST /{name}/stop`

### UI (static)
- `static/index.html` : dashboard moderne (CTA, stats, cartes), JS natif `fetch()` vers l’API REST.
  - Accès rapide H2 : `/formation/h2-console`
  - Accès API : `/formation/api/formations`
- Variante **Thymeleaf** possible (rendu serveur) : `templates/index.html` + contrôleur MVC.

---

## Système de plugins

### Contrats (stables)
```java
public enum PluginStatus { LOADED, STARTED, STOPPED, FAILED }

public interface Plugin {
    String getName();
    String getVersion();
    String getIcon();
    PluginStatus getStatus();
    void start() throws Exception;
    void stop()  throws Exception;
}
```

### Gestionnaire
```java
@Service
public class PluginManager {
  private final Map<String, Plugin> registry = new ConcurrentHashMap<>();
  public PluginManager(List<Plugin> plugins) { plugins.forEach(p -> registry.put(p.getName(), p)); }
  @PostConstruct void startAll() { registry.values().forEach(p -> { try { p.start(); } catch (Exception ignored) {} }); }
  public List<Plugin> list()                { return List.copyOf(registry.values()); }
  public boolean start(String name)         { var p = registry.get(name); if (p==null) return false; try { p.start(); return true; } catch(Exception e){ return false; } }
  public boolean stop(String name)          { var p = registry.get(name); if (p==null) return false; try { p.stop();  return true; } catch(Exception e){ return false; } }
  public PluginStatus statusOf(String name) { var p = registry.get(name); return p!=null ? p.getStatus() : null; }
}
```

### Plugins *in-process* (auto-détectés)
Chaque plugin est un `@Component` qui implémente `Plugin`.

---

## Ajouter une nouvelle fonctionnalité (plugin)

1) Créer une classe dans `core/src/main/java/com/formation/core/plugin/impl/`.
2) (Optionnel) nouvelles API REST.
3) (Optionnel) nouvelles entités JPA.
4) UI : ajouter les éléments dans `index.html`.
5) Build & run : `mvn -pl core clean package spring-boot:repackage && java -jar core/target/core-1.0.0.jar`.

---

## Séparation des responsabilités
- **core** : cœur métier + API + contrats plugin.
- **plugins** : fonctionnalités additionnelles.
- **pluginloader** : chargement dynamique.
- **api/interfacecli** : points d’accès alternatifs.

---

## Tests
- Unitaires : JUnit 5.
- Intégration : `@SpringBootTest` + H2.

---

## Déploiement
- Fat JAR : `mvn -pl core clean package spring-boot:repackage`.
- Config externe par variables d’environnement.

---

## Roadmap
- Extraction des plugins en JAR.
- Bus d’événements pour communication plugins ↔ core.
- Versionnage du contrat Plugin.
- UI micro-front (React/Vite) consommant `/api`.
